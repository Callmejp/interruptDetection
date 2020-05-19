package com.ecnu.tool;

import com.ecnu.auxiliary.ArraySymbol;
import com.ecnu.auxiliary.IntSymbol;
import com.ecnu.auxiliary.Symbol;
import com.ecnu.grammer.CBaseVisitor;
import com.ecnu.grammer.CParser;

import java.util.HashMap;
import java.util.Objects;


/**
 * visit方法尽量从上到下依次访问语法树的节点
 * 并模拟运行程序
 */
public class ASTEvaluator extends CBaseVisitor<Object>{
    private volatile HashMap<String, Symbol> localScope;
    private int currentLineCount;
    private volatile int pID;

    ASTEvaluator(int pID, int startLineCount) {
        this.localScope  = new HashMap<>();
        this.currentLineCount = startLineCount;
        this.pID = pID;
    }

    private void queryInterrupt() {
        // 抢占程序号不可能是主程序
        for (int interID = Config.segCount-1; interID >= 1; interID --) {
            // 运行到中断发生处，且此时中断没有被屏蔽
            // System.out.println("中断比对： " + ConcurrentControl.interHappenPlace[interID] + " " + this.currentLineCount);
            if (ConcurrentControl.interHappenPlace[interID] == this.currentLineCount
             && ConcurrentControl.interControl[interID]) {
                // 既然能运行，当前的运行pID肯定就是自己，所以先把当前运行id改为中断的pID
                // 并在栈中记录当前线程的pID以便之后依次被唤醒，
                // 唤醒抢占线程，最后再await自己
                ConcurrentControl.pIDOfCurrentProgram = interID;
                ConcurrentControl.notifyOrder.push(pID);
                // System.out.println("唤醒: " + interID);
                ConcurrentControl.conditions[interID].signal();
                try {
                    while (ConcurrentControl.pIDOfCurrentProgram != pID) {
                        // System.out.println("线程 " + pID + " 进入等待");
                        ConcurrentControl.conditions[pID].await();
                        // System.out.println("改了看不到？ " + ConcurrentControl.pIDOfCurrentProgram);
                    }
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

            }
        }
        // 默认在执行前完成了此语句，这样逻辑归于了一处
        this.currentLineCount += 1;
    }

    private Symbol lookForVariable(String variableName) {
        // 先从localScope寻找，再从MainScope寻找
        // 其中数组变量应该被分到expression节点中进行判读
        Symbol temp = null;
        if (this.localScope.get(variableName) != null) {
            temp = this.localScope.get(variableName);
        } else if (AnnotatedTree.MainScope.get(variableName) != null) {
            temp = AnnotatedTree.MainScope.get(variableName);
        } else {
            System.out.println("Found undeclared variables in visitPrimary Part!");
        }
        return temp;
    }

    private void findBug(CParser.ExpressionContext ctx) {
        // 如果已经找到一个Bug，就不需要再找了
        if (ResultLog.hasFoundBug) return;

        // 读操作
        String rightPart = ctx.expression(1).getText();
        // 写操作
        String leftPart = ctx.expression(0).getText();
        // 扫描所有的全局变量
        for (String variableName : AnnotatedTree.MainScope.keySet()) {
            // 本线程之前是否读过或者写过
            boolean ownReadBefore = ResultLog.readRecord.get(pID).get(variableName);
            boolean ownWriteBefore = ResultLog.writeRecord.get(pID).get(variableName);
            // 其它线程之前是否读过或者写过
            boolean otherWriteBefore = false;
            boolean otherReadBefore = false;

            if (rightPart.contains(variableName)) {
                for (int i=pID+1; i<Config.segCount; i++) {
                    otherWriteBefore = otherWriteBefore || ResultLog.writeRecord.get(i).get(variableName);
                }
                // 满足Bug Pattern，其中本线程接下来肯定要读了
                // R W R
                if (ownReadBefore && otherWriteBefore) {
                    ResultLog.logSpecificInfo(pID, variableName, 0);
                }
                // W W R
                else if (ownWriteBefore && otherWriteBefore) {
                    ResultLog.logSpecificInfo(pID, variableName, 1);
                } else {
                    // 覆盖其它线程的操作有效性
                    for (int i=pID+1; i<Config.segCount; i++) {
                        ResultLog.writeRecord.get(i).put(variableName, false);
                    }
                }
                ResultLog.readRecord.get(pID).put(variableName, true);

            } else if (leftPart.contains(variableName)) {
                for (int i=pID+1; i<Config.segCount; i++) {
                    otherWriteBefore = otherWriteBefore || ResultLog.writeRecord.get(i).get(variableName);
                    otherReadBefore = otherReadBefore || ResultLog.readRecord.get(i).get(variableName);
                }
                // W R W
                if (ownWriteBefore && otherReadBefore) {
                    ResultLog.logSpecificInfo(pID, variableName, 2);
                }
                // R W W
                else if (ownReadBefore && otherWriteBefore) {
                    ResultLog.logSpecificInfo(pID, variableName, 3);
                } else {
                    // 覆盖其它线程的操作有效性
                    for (int i=pID+1; i<Config.segCount; i++) {
                        ResultLog.readRecord.get(i).put(variableName, false);
                        ResultLog.writeRecord.get(i).put(variableName, false);
                    }
                }
                ResultLog.writeRecord.get(pID).put(variableName, true);


            }

        }

    }

    private void assignmentOperation(CParser.ExpressionContext ctx, Integer updatedValue) {
        Symbol temp;
        Integer index = 0;
        String name = ctx.getText();

        if (name.contains("[")) {
            name = ctx.expression(0).getText();
            temp = lookForVariable(name);
            index = (Integer) visitExpression(ctx.expression(1));
        } else {
            temp = lookForVariable(name);
        }
        // TODO: 此处写法逻辑上不会出问题，但还是有待商榷
        // System.out.println("准备修改变量 " + name + temp);
        Objects.requireNonNull(temp);
        temp.assignValue(updatedValue, index);
        // 因为引用指向同一个对象，所以assignValue已经将改动反映到实例中
        // this.localScope.put(name, temp);
    }

    @Override
    public Object visitFunctionDeclaration(CParser.FunctionDeclarationContext ctx) {
        return visitFunctionBody(ctx.functionBody());
    }

    @Override
    public Object visitFunctionBody(CParser.FunctionBodyContext ctx) {
        Object rtn = null;
        if (ctx.block() != null) {
            rtn = visitBlock(ctx.block());
        }
        return rtn;
    }

    @Override
    public Object visitBlock(CParser.BlockContext ctx) {
        return visitBlockStatements(ctx.blockStatements());
    }

    @Override
    public Object visitBlockStatements(CParser.BlockStatementsContext ctx) {
        Object rtn = null;
        for (CParser.BlockStatementContext child : ctx.blockStatement()) {
            rtn = visitBlockStatement(child);
        }
        return rtn;
    }

    @Override
    public Object visitBlockStatement(CParser.BlockStatementContext ctx) {
        Object rtn = null;
        if (ctx.variableDeclarators() != null) {
            // TODO: 此处为指定的需要统计的代码行，使用TODO高亮标记
            queryInterrupt();
            // this.currentLineCount += 1;
            // 调试使用
            // System.out.println("BlockStatement --- pID: " + pID + " : " + ctx.getText());

            rtn = visitVariableDeclarators(ctx.variableDeclarators());
        } else if (ctx.statement() != null) {
            rtn = visitStatement(ctx.statement());
        }
        return rtn;
    }

    @Override
    public Object visitStatement(CParser.StatementContext ctx) {
        Object rtn = null;
        if (ctx.statementExpression != null) {
            // TODO: 此处为指定的需要统计的代码行，使用TODO高亮标记。 且逻辑与@LineCountScanner中一致
            String statement = ctx.getText();
            if (statement.length() - statement.replaceAll(";", "").length() == 1
                    && (! statement.contains("if"))
                    && (! statement.contains("for"))
                    && (! statement.contains("else")
                    && (! statement.contains("{")))) {
                queryInterrupt();
                // this.currentLineCount += 1;
                // 调试使用
                // System.out.println("Statement --- pID: " + pID + " : " + statement + "(Line: " + currentLineCount + ")");
            }
            // 其中statementExpression 即为 expression
            rtn = visitExpression(ctx.statementExpression);
        } else if (ctx.IF() != null) {
            Boolean condition = (Boolean) visitParExpression(ctx.parExpression());
            // System.out.println(ctx.parExpression().getText() + " " + condition);
            if (condition) {
                rtn = visitStatement(ctx.statement(0));
            } else if (ctx.ELSE() != null) {
                rtn = visitStatement(ctx.statement(1));
            }
        }
        //for循环
        else if (ctx.FOR() != null) {
            CParser.ForControlContext forControl = ctx.forControl();
            // 初始化部分执行一次
            if (forControl.forInit() != null) {
                rtn = visitForInit(forControl.forInit());
            }

            while (true) {
                Boolean condition = true; // 如果没有条件判断部分，意味着一直循环
                if (forControl.expression() != null) {
                    // 应该返回Boolean值
                    condition = (Boolean) visitExpression(forControl.expression());
                }
                if (condition) {
                    // 执行for的语句体
                    rtn = visitStatement(ctx.statement(0));
                    // 执行forUpdate，通常是“i++”这样的语句。这个执行顺序不能出错。
                    // forUpdate即expressionList的别名
                    if (forControl.forUpdate != null) {
                        visitExpressionList(forControl.forUpdate);
                    }
                } else {
                    break;
                }
            }
        }
        //block
        else if (ctx.blockLabel != null) {
            rtn = visitBlock(ctx.blockLabel);

        }

        return rtn;
    }


    @Override
    public Object visitForInit(CParser.ForInitContext ctx) {
        Object rtn = null;
        if (ctx.variableDeclarators() != null) {
            rtn = visitVariableDeclarators(ctx.variableDeclarators());
        } else if (ctx.expressionList() != null) {
            rtn = visitExpressionList(ctx.expressionList());
        }
        return rtn;
    }


    @Override
    public Object visitVariableDeclarators(CParser.VariableDeclaratorsContext ctx) {
        Object rtn = null;
        for (CParser.VariableDeclaratorContext child : ctx.variableDeclarator()) {
            rtn = visitVariableDeclarator(child);
        }
        return rtn;
    }

    @Override
    public Object visitVariableDeclarator(CParser.VariableDeclaratorContext ctx) {
        // C语言默认初值为0
        Integer rtn = 0;
        Symbol temp;

        String variableName = visitVariableDeclaratorId(ctx.variableDeclaratorId());
        if (ctx.variableInitializer() != null) {
            // 初值应该为Integer类型
            rtn = visitVariableInitializer(ctx.variableInitializer());
        }
        // 有下标，为数组
        if (ctx.variableDeclaratorId().expression() != null) {
            int arraySize = (Integer) visitExpression(ctx.variableDeclaratorId().expression());
            int[] value = new int[arraySize];
            while (arraySize >= 1) {
                value[arraySize - 1] = rtn;
                arraySize --;
            }
            temp = new ArraySymbol(variableName, value);
        } else {
            // 否则为int变量
            temp = new IntSymbol(variableName, rtn);
        }

        // System.out.println("put 变量：" + variableName);
        this.localScope.put(variableName, temp);
        return null;
    }

    @Override
    public String visitVariableDeclaratorId(CParser.VariableDeclaratorIdContext ctx) {
        // 直接返回变量名
        return ctx.IDENTIFIER().getText();
    }

    @Override
    public Integer visitVariableInitializer(CParser.VariableInitializerContext ctx) {
        Integer rtn = null;
        if (ctx.expression() != null) {
            try {
                rtn = (Integer) visitExpression(ctx.expression());
            } catch (ClassCastException cce) {
                cce.printStackTrace();
            }
        }
        return rtn;
    }

    @Override
    public Object visitParExpression(CParser.ParExpressionContext ctx) {
        return visitExpression(ctx.expression());
    }

    @Override
    public Object visitExpressionList(CParser.ExpressionListContext ctx) {
        Object rtn = null;
        for (CParser.ExpressionContext child : ctx.expression()) {
            rtn = visitExpression(child);
        }
        return rtn;
    }

    @Override
    public Object visitExpression(CParser.ExpressionContext ctx) {
        Object rtn = null;
        if (ctx.bop != null && ctx.expression().size() >= 2) {
            Object left = visitExpression(ctx.expression(0));
            Object right = visitExpression(ctx.expression(1));
            // System.out.println(left + "==" + right);

            switch (ctx.bop.getType()) {
                case CParser.ADD:
                    rtn = (Integer)left + (Integer)right;
                    break;
                case CParser.SUB:
                    rtn = (Integer)left - (Integer)right;
                    break;
                case CParser.MUL:
                    rtn = (Integer)left * (Integer)right;
                    break;
                case CParser.DIV:
                    rtn = (Integer)left / (Integer)right;
                    break;
                case CParser.EQUAL:
                    rtn = left.equals(right);
                    break;
                case CParser.NOTEQUAL:
                    rtn = ! left.equals(right);
                    break;
                case CParser.LE:
                    rtn = (Integer)left <= (Integer)right;
                    break;
                case CParser.LT:
                    rtn = (Integer)left < (Integer)right;
                    break;
                case CParser.GE:
                    rtn = (Integer)left >= (Integer)right;
                    break;
                case CParser.GT:
                    rtn = (Integer)left > (Integer)right;
                    break;
                case CParser.AND:
                    rtn = (Boolean) left && (Boolean) right;
                    break;
                case CParser.OR:
                    rtn = (Boolean) left || (Boolean) right;
                    break;
                case CParser.ASSIGN:
                    // TODO: 重要的赋值语句，以TODO高亮标记(因为全局变量的读和写默认只发生在这里)
                    findBug(ctx);
                    assignmentOperation( ctx.expression(0), (Integer)visitExpression(ctx.expression(1)) );
                    break;

                default:
                    break;
            }
        }
        // 字面量
        else if (ctx.primary() != null) {
            rtn = visitPrimary(ctx.primary());
        }
        // 后缀运算，例如：i++ 或 i--
        else if (ctx.postfix != null) {
            Object value = visitExpression(ctx.expression(0));

            switch (ctx.postfix.getType()) {
                case CParser.INC:
                    rtn = (Integer)value + 1;
                    break;
                case CParser.DEC:
                    rtn = (Integer)value - 1;
                    break;
                default:
                    break;
            }
            assignmentOperation(ctx.expression(0), (Integer) rtn);
        }
        //前缀操作，例如：++i 或 --i
        else if (ctx.prefix != null) {
            Object value = visitExpression(ctx.expression(0));

            switch (ctx.prefix.getType()) {
                case CParser.INC:
                    rtn = (Integer)value + 1;
                    assignmentOperation(ctx.expression(0), (Integer) rtn);
                    break;
                case CParser.DEC:
                    rtn = (Integer)value - 1;
                    assignmentOperation(ctx.expression(0), (Integer) rtn);
                    break;
                // '!'，即逻辑非运算
                case CParser.BANG:
                    rtn = !((Boolean) value);
                    break;
                default:
                    break;
            }
        }
        // functionCall
        else if (ctx.functionCall() != null) {
            rtn = visitFunctionCall(ctx.functionCall());
        }
        // array
        else if (ctx.bop == null && ctx.expression().size() >= 2 && ctx.getText().contains("[")) {
            // 数组只靠变量名取不出来值，直接处理掉
            String variableName = ctx.expression(0).getText();
            // 通过递归计算下标的值
            Integer index = (Integer) visitExpression(ctx.expression(1));
            Symbol temp = lookForVariable(variableName);
            if(temp != null)return temp.getValue(index);
            else return null;
        }
        return rtn;
    }

    @Override
    public Object visitFunctionCall(CParser.FunctionCallContext  ctx) {
        // queryInterrupt();

        Integer param = null;
        String functionName = ctx.IDENTIFIER().getText();

        if (ctx.expressionList() != null) {
            try {
                param = (Integer) visitExpressionList(ctx.expressionList());
            } catch (ClassCastException cce) {
                cce.printStackTrace();
            }
        }


        if (functionName.contains(Config.closeInterruptName)) {
            Objects.requireNonNull(param);
            // System.out.println(param);
            ConcurrentControl.interControl[param] = false;
        } else if (functionName.contains(Config.openInterruptName)) {
            Objects.requireNonNull(param);
            // System.out.println(param);
            ConcurrentControl.interControl[param] = true;
        } else {
            // System.out.println("trivial function name: " + functionName);
        }
        return null;
    }

    @Override
    public Object visitPrimary(CParser.PrimaryContext ctx) {
        Object rtn = null;
        String variableName = ctx.getText();
        //字面量
        if (ctx.literal() != null) {
            rtn = visitLiteral(ctx.literal());
        }
        //变量
        else if (ctx.IDENTIFIER() != null) {
            // System.out.println("look for var:" + variableName);
            Symbol temp = lookForVariable(variableName);
            if (temp != null)return temp.getValue();
            else return null;
        }
        //括号括起来的表达式
        else if (ctx.expression() != null){
            rtn = visitExpression(ctx.expression());
        }
        return rtn;
    }

    @Override
    public Object visitLiteral(CParser.LiteralContext ctx) {
        Object rtn = null;
        //整数
        if (ctx.integerLiteral() != null) {
            rtn = visitIntegerLiteral(ctx.integerLiteral());
        } else {
            System.out.println(ctx.getText());
            System.out.println("Error in ASTEvaluator.java: visitLiteral");
        }
        return rtn;
    }

    @Override
    public Object visitIntegerLiteral(CParser.IntegerLiteralContext ctx) {
        String literal = ctx.getText();
        if (literal.contains("0x")) {
            return Integer.valueOf(literal.substring(2), 16);
        }
        return Integer.valueOf(ctx.getText());
    }



}
