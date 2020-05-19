package com.ecnu.grammer;
// Generated from C.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(CParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(CParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBodyDeclaration(CParser.ClassBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#memberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaration(CParser.MemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(CParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(CParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeTypeOrVoid(CParser.TypeTypeOrVoidContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedNameList(CParser.QualifiedNameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(CParser.FormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#formalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterList(CParser.FormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(CParser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLastFormalParameter(CParser.LastFormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#variableModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableModifier(CParser.VariableModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(CParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(CParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(CParser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#variableDeclarators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarators(CParser.VariableDeclaratorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(CParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorId(CParser.VariableDeclaratorIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#variableInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializer(CParser.VariableInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(CParser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceType(CParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgument(CParser.TypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(CParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(CParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#floatLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(CParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(CParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(CParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#blockStatements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatements(CParser.BlockStatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(CParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(CParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroup(CParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabel(CParser.SwitchLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#forControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForControl(CParser.ForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(CParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#enhancedForControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForControl(CParser.EnhancedForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#parExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpression(CParser.ParExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(CParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(CParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(CParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(CParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#typeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeList(CParser.TypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#typeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeType(CParser.TypeTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#functionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionType(CParser.FunctionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(CParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreator(CParser.CreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#superSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperSuffix(CParser.SuperSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link CParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(CParser.ArgumentsContext ctx);
}