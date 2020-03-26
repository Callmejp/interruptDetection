package com.ecnu.tool;

/**
 * Define the state of Lexical Analysis Automata
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
public enum State {
    VO1(1, "VOLATILE & VOID 1"), VO2(2, "VOLATILE & VOID 2"), VOLATILE3(3, "VOLATILE3"),
    I1(4, "INT & IF 1"), INT2(5, "INT2"), INT3(6, "INT3"),
    DEFINE1(7, "DEFINE1"), DEFINE2(8, "DEFINE2"),
    VOID3(12, "VOID3"),
    FOR1(13, "FOR1"), FOR2(14, "FOR2"), FOR3(15, "FOR3"),
    IF2(17, "IF2"),
    ELSE1(18, "ELSE1"), ELSE2(19, "ELSE2"), ELSE3(20, "ELSE3"), ELSE4(21, "ELSE4"),

    RightParentheses(22, "RightParentheses"), LeftParentheses(23, "LeftParentheses"),
    RightBrackets(24, "RightBrackets"), LeftBrackets(25, "LeftBrackets"),
    RightBrace(26, "RightBrace"), LeftBrace(27, "LeftBrace"),
    Assignment(28, "Assignment"), Equal(29, "Equal"),   // = | ==
    Less(30, "Less"), LessEqual(31, "LessEqual"),       // < | <=
    Great(32, "Great"), GreatEqual(33, "GreatEqual"),   // > | >=
    Not(34, "Not"), NotEqual(35, "NotEqual"),           // ! | !=
    Plus(36, "Plus"), PlusPlus(37, "PlusPlus"),         // + | ++
    Minus(38, "Minus"), MinusMinus(39, "MinusMinus"),   // - | --
    Comma(40, "Comma"), Semicolon(41, "Semicolon"),     // , | ;

    VARIABLE(42, "VARIABLE"),
    LITERAL(43, "LITERAL"),

    INITIAL(42, "INITIAL");

    private int index;
    private String describeInfo;

    public int getIndex() {
        return index;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    State(int id, String info) {
        this.index = id;
        this.describeInfo = info;
    }

}
