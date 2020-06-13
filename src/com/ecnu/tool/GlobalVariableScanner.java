package com.ecnu.tool;

import com.ecnu.auxiliary.ArraySymbol;
import com.ecnu.auxiliary.IntSymbol;
import com.ecnu.auxiliary.Symbol;
import com.ecnu.grammer.CBaseListener;
import com.ecnu.grammer.CParser;


/**
 * 初始化全局变量并保存到MAP中；
 * 绑定函数名和其对应的ParseTree节点；
 * 其中的enter/exit方法均继承于Antlr提供的监听器类。
 *
 * inFunction: 用来区分全局段和各函数段的标志
 */
public class GlobalVariableScanner extends CBaseListener {
    private boolean inFunction;

    GlobalVariableScanner() {
        this.inFunction = false;
    }


    public void enterFunctionDeclaration(CParser.FunctionDeclarationContext ctx) {
        // System.out.print("Enter Function: ");
        this.inFunction = true;
        String functionName = ctx.IDENTIFIER().getText();
        AnnotatedTree.rootOfSubProgram.put(functionName, ctx);
        // System.out.println(functionName);
    }

    public void exitFunctionDeclaration(CParser.FunctionDeclarationContext ctx) {
        // System.out.println("Exit Function");
        this.inFunction = false;
        // System.out.println(ctx.IDENTIFIER().getText());
    }

    public void enterStatement(CParser.StatementContext ctx) {
        if (!this.inFunction) {
            if (ctx.IncludeDirective() != null) {
                // System.out.println("include statement: " + ctx.IncludeDirective().getText());
            } else if (ctx.ComplexDefine() != null) {
                // System.out.println("define statement: " + ctx.ComplexDefine().getText());
                // 去掉换行符后以空格分隔字符串
                // define(info[0])  Var(info[1])  Value(info[2])
                String[] info = ctx.ComplexDefine().getText().replaceAll("\r|\n", "").split(" ");
                Integer value = Integer.parseInt(info[2]);
                AnnotatedTree.MainScope.put(
                        info[1],
                        new IntSymbol(info[1], value)
                );
            }
        }
    }

    public void enterVariableDeclarators(CParser.VariableDeclaratorsContext ctx) {
        if (!inFunction) {
            // C语言可以同时初始化多个变量，需要遍历
            for (CParser.VariableDeclaratorContext child : ctx.variableDeclarator()) {
                String variableName = child.variableDeclaratorId().IDENTIFIER().getText();
                // System.out.println(variableName);

                Symbol temp = new IntSymbol(variableName, null);
                // expression 就是数组的下标
                if (child.variableDeclaratorId().expression() != null) {
                    Integer arraySize = AnnotatedTree.strToInteger(child.variableDeclaratorId().expression().getText());
                    int[] value = new int[arraySize];
                    if (child.variableInitializer() != null) {
                        Integer t = AnnotatedTree.strToInteger(child.variableInitializer().getText());
                        while(arraySize >= 1) {
                            value[arraySize - 1] = t;
                            arraySize --;
                        }
                    }
                    temp = new ArraySymbol(variableName, value);
                }else if (child.variableInitializer() != null) {
                    ((IntSymbol) temp).value = AnnotatedTree.strToInteger(child.variableInitializer().getText());
                }
                AnnotatedTree.MainScope.put(
                        variableName,
                        temp
                );
            }
        }
    }
}
