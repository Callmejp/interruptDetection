package com.ecnu.grammer;
// Generated from C.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CParser}.
 */
public interface CListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(CParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(CParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(CParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(CParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassBodyDeclaration(CParser.ClassBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassBodyDeclaration(CParser.ClassBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(CParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(CParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(CParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(CParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(CParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(CParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void enterTypeTypeOrVoid(CParser.TypeTypeOrVoidContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void exitTypeTypeOrVoid(CParser.TypeTypeOrVoidContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedNameList(CParser.QualifiedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedNameList(CParser.QualifiedNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(CParser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(CParser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterList(CParser.FormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterList(CParser.FormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(CParser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(CParser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterLastFormalParameter(CParser.LastFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitLastFormalParameter(CParser.LastFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void enterVariableModifier(CParser.VariableModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void exitVariableModifier(CParser.VariableModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(CParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(CParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(CParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(CParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(CParser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(CParser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarators(CParser.VariableDeclaratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarators(CParser.VariableDeclaratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(CParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(CParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorId(CParser.VariableDeclaratorIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorId(CParser.VariableDeclaratorIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(CParser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(CParser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(CParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(CParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceType(CParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceType(CParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgument(CParser.TypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgument(CParser.TypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(CParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(CParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(CParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(CParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(CParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(CParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(CParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(CParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(CParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(CParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#blockStatements}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatements(CParser.BlockStatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#blockStatements}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatements(CParser.BlockStatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(CParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(CParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(CParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(CParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(CParser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(CParser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#forControl}.
	 * @param ctx the parse tree
	 */
	void enterForControl(CParser.ForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#forControl}.
	 * @param ctx the parse tree
	 */
	void exitForControl(CParser.ForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(CParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(CParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#enhancedForControl}.
	 * @param ctx the parse tree
	 */
	void enterEnhancedForControl(CParser.EnhancedForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#enhancedForControl}.
	 * @param ctx the parse tree
	 */
	void exitEnhancedForControl(CParser.EnhancedForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(CParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(CParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(CParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(CParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(CParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(CParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(CParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(CParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#typeList}.
	 * @param ctx the parse tree
	 */
	void enterTypeList(CParser.TypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#typeList}.
	 * @param ctx the parse tree
	 */
	void exitTypeList(CParser.TypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#typeType}.
	 * @param ctx the parse tree
	 */
	void enterTypeType(CParser.TypeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#typeType}.
	 * @param ctx the parse tree
	 */
	void exitTypeType(CParser.TypeTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#functionType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionType(CParser.FunctionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#functionType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionType(CParser.FunctionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(CParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(CParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(CParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(CParser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void enterSuperSuffix(CParser.SuperSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void exitSuperSuffix(CParser.SuperSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link CParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(CParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(CParser.ArgumentsContext ctx);
}