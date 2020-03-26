package com.ecnu.tool;


import java.util.ArrayList;

/**
 * Lexical Analyzer
 * ---------------------------------------------
 * Key Words:
 * volatile, int, #define, void, for, if, else, else if
 * ---------------------------------------------
 * Special Character:
 * ), (, ], [, }, {, =, ==, <, <=, >, >=, !, !=, ++, --, ;, +, -, ,;
 * ---------------------------------------------
 * Others:
 * Variable Name(simple_irs_001), Identifier(1000, 0xaa)
 */
final class Parser {
    private static State currentState = State.INITIAL;
    private static StringBuilder sb = new StringBuilder();

    private static void reset() {
        sb.setLength(0);
        currentState = State.INITIAL;
    }

    /**
     * change the currentState based on the next character
     * @param ch the next character
     */
    private static void initializeState(char ch) {
        if(Character.isAlphabetic(ch)) {
            if (ch == 'v')
                currentState = State.VO1;
            else if (ch == 'i')
                currentState = State.I1;
            else if (ch == '#')
                currentState = State.DEFINE1;
            else if (ch == 'f')
                currentState = State.FOR1;
            else if (ch == 'e')
                currentState = State.ELSE1;
            else{
                currentState = State.VARIABLE;
            }
        }else if(Character.isDigit(ch)) {
            currentState = State.LITERAL;
        }else if(ch == ')') {
            currentState = State.RightParentheses;
        }else if(ch == '(') {
            currentState = State.LeftParentheses;
        }else if(ch == ']') {
            currentState = State.RightBrackets;
        }else if(ch == '[') {
            currentState = State.LeftBrackets;
        }else if(ch == '}') {
            currentState = State.RightBrace;
        }else if(ch == '{') {
            currentState = State.LeftBrace;
        }else if(ch == '=') {
            currentState = State.Assignment;
        }else if(ch == '>') {
            currentState = State.Great;
        }else if(ch == '<') {
            currentState = State.Less;
        }else if(ch == '!') {
            currentState = State.Not;
        }else if(ch == '+') {
            currentState = State.Plus;
        }else if(ch == '-') {
            currentState = State.Minus;
        }else if(ch == ',') {
            currentState = State.Comma;
        }else if(ch == ';') {
            currentState = State.Semicolon;
        }else if(ch == ' ') {
            currentState = State.INITIAL;
        }else if(ch == '#') {
            currentState = State.DEFINE1;
        }else{
            currentState = State.VARIABLE;
        }
    }

    /**
     * Main character analysis to the code
     * @param codeContent string of code
     * @param tokenList in the Main Method
     * @param valueList in the Main Method
     */
    static void parseCode(String codeContent, ArrayList<Token> tokenList, ArrayList<String> valueList) {

        char[] codeArray = codeContent.toCharArray();
        for(int i=0;i<codeArray.length;i++) {
            // if(i >= 50) System.out.println(tokenList.get(tokenList.size()-1) + ": " + valueList.get(tokenList.size()-1) + " " + i);
            char ch = codeArray[i];
            if (ch != ' ') {
                sb.append(ch);
            }
            // -------------------------------------
            if (currentState == State.INITIAL) {
                initializeState(ch);
                continue;
            }else if (currentState == State.VO1) {
                if (ch == 'o') {
                    currentState = State.VO2;
                    continue;
                }
            }else if (currentState == State.VO2) {
                if (ch == 'l') {
                    currentState = State.VOLATILE3;
                    continue;
                } else if (ch == 'i') {
                    currentState = State.VOID3;
                    continue;
                }
            }else if (currentState == State.VOLATILE3) {
                // assume no variable starts with 'vol'
                if (ch == ' ') {
                    tokenList.add(Token.VOLATILE);
                    valueList.add("volatile");
                    reset();
                }
                continue;
            }else if (currentState == State.VOID3) {
                // assume no variable starts with 'voi'
                if(ch == ' ') {
                    tokenList.add(Token.VOID);
                    valueList.add("void");
                    reset();
                }
                continue;
            }else if (currentState == State.I1) {
                if(ch == 'n') {
                    currentState = State.INT2;
                    continue;
                }else if(ch == 'f') {
                    currentState = State.IF2;
                    continue;
                }
            }else if (currentState == State.INT2) {
                if(ch == 't') {
                    currentState = State.INT3;
                    continue;
                }
            }else if (currentState == State.INT3) {
                if(ch == ' ') {
                    tokenList.add(Token.INT);
                    valueList.add("int");
                    reset();
                    continue;
                }
            }else if (currentState == State.IF2) {
                // assume no variable starts with 'if'
                tokenList.add(Token.IF);
                valueList.add("if");
                reset();
                initializeState(ch);
                continue;
            }else if (currentState == State.DEFINE1) {
                // assume no variable starts with '#define'
                if(ch == ' ') {
                    tokenList.add(Token.DEFINE);
                    valueList.add("#define");
                    reset();
                }
                continue;
            }else if (currentState == State.FOR1) {
                if(ch == 'o') {
                    currentState = State.FOR2;
                    continue;
                }
            }else if (currentState == State.FOR2) {
                if(ch == 'r') {
                    currentState = State.FOR3;
                    continue;
                }
            }else if (currentState == State.FOR3) {
                tokenList.add(Token.FOR);
                valueList.add("for");
                reset();
                initializeState(ch);
                continue;
            }else if (currentState == State.ELSE1) {
                if(ch == 'l') {
                    currentState = State.ELSE2;
                    continue;
                }
            }else if (currentState == State.ELSE2) {
                if(ch == 's') {
                    currentState = State.ELSE3;
                    continue;
                }
            }else if (currentState == State.ELSE3) {
                // assume no variable starts with 'else'
                if(ch == 'e') {
                    tokenList.add(Token.ELSE);
                    valueList.add("else");
                    reset();
                }
                continue;
            }else if (currentState == State.RightParentheses || currentState == State.LeftParentheses
                    ||currentState == State.RightBrackets    || currentState == State.LeftBrackets
                    ||currentState == State.RightBrace       || currentState == State.LeftBrace
                    ||currentState == State.Comma            || currentState == State.Semicolon) {
                // reduce the repeat of code about ') ( ] [ } { ; ,'
                if (currentState == State.RightParentheses) {
                    tokenList.add(Token.RightParentheses);
                    valueList.add(")");
                } else if (currentState == State.LeftParentheses) {
                    tokenList.add(Token.LeftParentheses);
                    valueList.add("(");
                } else if (currentState == State.RightBrackets) {
                    tokenList.add(Token.RightBrackets);
                    valueList.add("]");
                } else if (currentState == State.LeftBrackets) {
                    tokenList.add(Token.LeftBrackets);
                    valueList.add("[");
                } else if (currentState == State.RightBrace) {
                    tokenList.add(Token.RightBrace);
                    valueList.add("}");
                } else if (currentState == State.LeftBrace) {
                    tokenList.add(Token.LeftBrace);
                    valueList.add("{");
                } else if (currentState == State.Semicolon) {
                    tokenList.add(Token.Semicolon);
                    valueList.add(";");
                } else if (currentState == State.Comma) {
                    tokenList.add(Token.Comma);
                    valueList.add(",");
                }
                sb.deleteCharAt(0);
                initializeState(ch);
                continue;
            }else if (currentState == State.Assignment) {
                reset();
                if(ch == '=') {
                    tokenList.add(Token.Equal);
                    valueList.add("==");
                }else {
                    tokenList.add(Token.Assignment);
                    valueList.add("=");
                    initializeState(ch);
                }
                continue;
            }else if (currentState == State.Less) {
                reset();
                if(ch == '=') {
                    tokenList.add(Token.LessEqual);
                    valueList.add("<=");
                }else {
                    tokenList.add(Token.Less);
                    valueList.add("<");
                    initializeState(ch);
                }
                continue;
            }else if (currentState == State.Great) {
                reset();
                if(ch == '=') {
                    tokenList.add(Token.GreatEqual);
                    valueList.add(">=");
                }else {
                    tokenList.add(Token.Great);
                    valueList.add(">");
                    initializeState(ch);
                }
                continue;
            }else if (currentState == State.Not) {
                reset();
                if(ch == '=') {
                    tokenList.add(Token.NotEqual);
                    valueList.add("!=");
                }else {
                    tokenList.add(Token.Not);
                    valueList.add("!");
                    initializeState(ch);
                }
                continue;
            }else if (currentState == State.Plus) {
                reset();
                if(ch == '+') {
                    tokenList.add(Token.PlusPlus);
                    valueList.add("++");
                }else {
                    tokenList.add(Token.Plus);
                    valueList.add("+");
                    initializeState(ch);
                }
                continue;
            }else if (currentState == State.Minus) {
                reset();
                if(ch == '-') {
                    tokenList.add(Token.MinusMinus);
                    valueList.add("--");
                }else {
                    tokenList.add(Token.Minus);
                    valueList.add("-");
                    initializeState(ch);
                }
                continue;
            }else if(currentState == State.LITERAL) {
                // assume no variable starts with 'number'
                // not Digit or Alpha indicate the end of LITERAL
                if(!Character.isDigit(ch) && !Character.isAlphabetic(ch)) {
                    i --;
                    if(ch != ' ')sb.deleteCharAt(sb.length()-1);
                    tokenList.add(Token.LITERAL);
                    valueList.add(sb.toString());
                    reset();
                }
                continue;
            }
            // we assume VARIABLE include all other cases
            currentState = State.VARIABLE;
            // characters below indicate the end of VARIABLE
            if(ch == ' ' || ch == '[' || ch == ';' || ch == '+' || ch == '-'
             ||ch == ']' || ch == '(' || ch == ')' || ch == ',') {
                i --;
                if(ch != ' ')sb.deleteCharAt(sb.length()-1);
                if(currentState == State.VARIABLE) {
                    tokenList.add(Token.VARIABLE);
                    valueList.add(sb.toString());
                    reset();
                }else {
                    System.out.println("Something unexpected happen near " + i + "th character");
                }
            }

        }
    }
}
