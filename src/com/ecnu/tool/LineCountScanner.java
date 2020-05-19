package com.ecnu.tool;

import com.ecnu.grammer.CBaseListener;
import com.ecnu.grammer.CParser;


/**
 * 统计各程序段的行数，便于后续的枚举抢占点
 */
class LineCountScanner extends CBaseListener {
    private String currentProgramName;
    private int loopCountOfForSegment;

    LineCountScanner() {
        this.currentProgramName = "";
        this.loopCountOfForSegment = 1;
    }

    public void enterFunctionDeclaration(CParser.FunctionDeclarationContext ctx) {
        this.currentProgramName = ctx.IDENTIFIER().getText();
        AnnotatedTree.lineOfSubProgram.put(currentProgramName, 0);
    }

    public void exitFunctionDeclaration(CParser.FunctionDeclarationContext ctx) {
        this.currentProgramName = "";
    }


    public void enterStatement(CParser.StatementContext ctx) {
        if (ctx.forControl() != null) {
            // TODO: 默认循环变量的Step为1, 其中当前的initialValue和finalValue为起始和终止值
            for (CParser.VariableDeclaratorContext child : ctx.forControl().forInit().variableDeclarators().variableDeclarator()) {
                String initialName = child.variableInitializer().getText();
                // System.out.println(initialName);
                Integer initialValue = AnnotatedTree.strToInteger(initialName);

                String finalName = ctx.forControl().expression().expression().get(1).getText();
                // System.out.println(finalName);
                Integer finalValue = AnnotatedTree.strToInteger(finalName);

                this.loopCountOfForSegment = Math.abs(initialValue - finalValue);
            }
        }else {
            String statement = ctx.getText();
            // 这里的想法是当前的语句只含有1个分号时，认为是单个语句。
            // 同时不能包含if/else/for，否则为复合语句。
            if (statement.length() - statement.replaceAll(";", "").length() == 1
             && (! statement.contains("if"))
             && (! statement.contains("for"))
             && (! statement.contains("else")
             && (! statement.contains("{")))) {
                int preLineCount = AnnotatedTree.lineOfSubProgram.get(currentProgramName);
                AnnotatedTree.lineOfSubProgram.put(currentProgramName, preLineCount + this.loopCountOfForSegment);
//                System.out.println("block add: " + this.loopCountOfForSegment);
//                System.out.println(ctx.getText());
            }
        }

    }

    public void exitStatement(CParser.StatementContext ctx) {
        if (ctx.forControl() != null) {
            this.loopCountOfForSegment = 1;
        }
    }


    public void enterBlockStatement(CParser.BlockStatementContext ctx) {
        if (ctx.variableDeclarators() != null
         && ! currentProgramName.equals("")) {
            int preLineCount = AnnotatedTree.lineOfSubProgram.get(currentProgramName);
            AnnotatedTree.lineOfSubProgram.put(currentProgramName, preLineCount + this.loopCountOfForSegment);
            // System.out.println("block add: " + this.loopCountOfForSegment);
        }
    }
}
