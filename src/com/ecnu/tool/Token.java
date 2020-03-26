package com.ecnu.tool;

/**
 * Define the token of all types
 * ---------------------------------------------
 * Key Words:
 * volatile, int, #define, void, for, if, else, else if
 * ---------------------------------------------
 * Special Character:
 * ), (, ], [, }, {, =, ==, <, <=, >, >=, !, !=, ++, --, ;, +, -;
 * ---------------------------------------------
 * Others:
 * Variable Name(simple_irs_001), Identifier(1000, 0xaa)
 */
public enum Token {
    VOLATILE(1, "VOLATILE"),
    INT(2, "INT"),
    DEFINE(3, "#DEFINE"),
    VOID(4, "VOID"),
    FOR(5, "FOR"),
    IF(6, "IF"),
    ELSE(7, "ELSE"),

    RightParentheses(8, "RightParentheses"), LeftParentheses(9, "LeftParentheses"),
    RightBrackets(10, "RightBrackets"), LeftBrackets(11, "LeftBrackets"),
    RightBrace(12, "RightBrace"), LeftBrace(13, "LeftBrace"),
    Assignment(14, "Assignment"), Equal(15, "Equal"),   // = | ==
    Less(16, "Less"), LessEqual(17, "LessEqual"),       // < | <=
    Great(18, "Great"), GreatEqual(19, "GreatEqual"),   // > | >=
    Not(20, "Not"), NotEqual(21, "NotEqual"),           // ! | !=
    Plus(22, "Plus"), PlusPlus(23, "PlusPlus"),         // + | ++
    Minus(24, "Minus"), MinusMinus(25, "MinusMinus"),   // - | --
    Comma(26, "Comma"), Semicolon(27, "Semicolon"),     // , | ;

    LITERAL(28, "LITERAL"),
    VARIABLE(29, "VARIABLE");

    private int index;
    private String typeInfo;

    public int getIndex() {
        return index;
    }

    public String getTypeInfo() {
        return typeInfo;
    }

    Token(int id, String info) {
        this.index = id;
        this.typeInfo = info;
    }

}
