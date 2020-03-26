package com.ecnu.tool;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Offer all (static) functions
 *
 * -----------Specific Instructions---------------
 * reset: reset the variables that requested
 * strToInteger: handle 0x(16) or Normal(10)
 * signForOperator: return 0~5 based the priority and the Operator Kind(Read / Write)
 * recurHandleCode: translate each sentence into DIY representation
 */
final class Util {

    // whether it's the first time loop
    private static boolean firstLoop = false;
    // tokenList count
    private static int i;
    // sentence count
    private static int j;
    // segment count
    private static Stack<Integer> segString = new Stack<>();
    // eval engine
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine se = manager.getEngineByName("js");


    static void reset(int startPosition) {
        j = 0;
        i = startPosition;
        segString.clear();
    }

    static void reset() {
        j = 0;
        segString.clear();
    }

    /**
     * translate the "123" to 123
     * @param number String form of the integer
     * @return integer
     */
    static Integer strToInteger(String number) {
        int ans, ifPositive = 1;

        if(number.startsWith("-")) {
            ifPositive = -1;
            number = number.substring(1);
        }

        if(number.contains("0x")) ans = Integer.parseInt(number.substring(2), 16);
        else ans = Integer.parseInt(number);

        return ans * ifPositive;
    }

    /**
     * if string is the name of variable, then return its value from Map
     * else return int
     * @param globalVariable Map where stores the value of global variable
     * @param name String: "123" / "Variable Name"
     * @return 123 / value according to the Variable
     */
    private static Integer strToInteger(HashMap<String, Integer> globalVariable, String name) {
        if (globalVariable.get(name) != null) {
            return globalVariable.get(name);
        }else return strToInteger(name);
    }

    /**
     *         Read   Write
     * MAIN:     0    1
     * SUB_1:    2    3
     * SUB_2:    4    5
     *
     * @param priority MAIN: 0 SUB_1: 1 SUB_2 : 2
     * @param Read if it's Read Operator
     * @return the specific sign identify six different situations
     */
    private static int signForOperator(int priority, boolean Read) {
        int add = 0;
        if(!Read)add = 1;
        return 2 * priority + add;
    }

    /**
     * Combine words into sentences and put them in turn into a Segment instance
     *
     * type: 0(disable) | 1(enable) | 2(declare) | 3(assignment)
     *       4(if)  | 5(else)
     *       6(for) |
     *       7({ for FOR)  |  8({ for IF)  |  9({ for ELSE) | 10({ for Segment)
     *       11(nothing useful)  |  12(Segment_End)
     *       13(special type for loop sentence)
     * @param tokenList which store the key-word/variable_name/literal... in order
     * @param valueList which store the actual string corresponding to the tokenList
     * @param seg see Segment.java
     */
    @SuppressWarnings("unchecked")
    static void recurHandleCode(ArrayList<Token> tokenList, ArrayList<String> valueList, Segment seg) {
        // whether need complete the } or start the {
        int needRightBrace = 0, needLeftBrace = 0;
        // store the negation of the last if condition
        ArrayList<String> else_tempUse = new ArrayList<>();
        ArrayList<Token> else_tokenUse = new ArrayList<>();

        for (; i < tokenList.size(); i++) {
            Token curToken = tokenList.get(i), nextOperator, nextToken;
            ArrayList<String> tempUse = new ArrayList<>();
            ArrayList<Token> tokenUse = new ArrayList<>();

            if (curToken == Token.VOID) {
                // VOID FUNCTION_NAME (   )   {
                // i     +1           +2  +3  +4
                i = i + 4;
            } else if (curToken == Token.FOR) {
                // for ( ... LITERAL/VARIABLE; ... LITERAL/VARIABLE; ... )
                for (; i != -1; i++) {
                    curToken = tokenList.get(i);
                    nextOperator = tokenList.get(i + 1);
                    nextToken = tokenList.get(i + 2);
                    if (
                       (curToken == Token.VARIABLE && nextOperator == Token.Assignment)
                    || (curToken == Token.VARIABLE && (nextToken == Token.VARIABLE || nextToken == Token.LITERAL))
                    || (curToken == Token.Semicolon && (nextToken == Token.PlusPlus || nextToken == Token.MinusMinus))
                    ) {
                        if (nextOperator == Token.Assignment) {
                            tempUse.add(valueList.get(i));
                            tokenUse.add(curToken);
                        }
                        // store initial value & final value & delta(+1/-1)
                        tempUse.add(valueList.get(i + 2));
                        tokenUse.add(nextToken);
                    }
                    if (curToken == Token.RightParentheses) break;
                }
                seg.sentences.add(new Sentence(tempUse, j++, 6, tokenUse));
                needLeftBrace = Config.getSignForFor();

            } else if (curToken == Token.IF) {
                // if ( VARIABLE OPERATOR VARIABLE )
                // i  +1  +2      +1       +2     +3
                else_tempUse.clear(); else_tokenUse.clear();
                else_tempUse.add("!("); else_tokenUse.add(Token.LITERAL);

                i = i + 2;
                for(;i!=-1;i++) {
                    if(tokenList.get(i) == Token.RightParentheses)break;
                    tempUse.add(valueList.get(i));   else_tempUse.add(valueList.get(i));
                    tokenUse.add(tokenList.get(i));  else_tokenUse.add(tokenList.get(i));
                }
                seg.sentences.add(new Sentence(tempUse, j++, 4, tokenUse));
                needLeftBrace = Config.getSignForIf();

                else_tempUse.add(")");
                else_tokenUse.add(Token.LITERAL);

            } else if (curToken == Token.ELSE) {
                // else or else if
                // TODO  array haven't been considered
                if (tokenList.get(i + 1) == Token.IF) {
                    // else if   (   X1  =  X2   )
                    // i    +1  +2   +3  +1  +2  +3
                    i += 3;
                    for(;i!=-1;i++) {
                        if(tokenList.get(i) == Token.RightParentheses)break;
                        tempUse.add(valueList.get(i));
                        tokenUse.add(tokenList.get(i));
                    }
                } else {
                    tempUse = (ArrayList<String>) else_tempUse.clone();
                    tokenUse = (ArrayList<Token>) else_tokenUse.clone();
                }
                seg.sentences.add(new Sentence(tempUse, j++, 5, tokenUse));
                needLeftBrace = Config.getSignForElse();

            } else if (curToken == Token.INT) {
                // int x, y = a, z[100];
                for (; i != -1; i++) {
                    ArrayList<String> localUse = new ArrayList<>();

                    curToken = tokenList.get(i);
                    nextOperator = tokenList.get(i + 1);
                    if (curToken == Token.VARIABLE) {
                        localUse.add(valueList.get(i));
                        if (nextOperator == Token.Assignment) {
                            // y = a
                            localUse.add(valueList.get(i + 2));
                            seg.sentences.add(new Sentence(localUse, j++, 3));
                        } else if (nextOperator == Token.LeftBrackets) {
                            // z[100]
                            seg.sentences.add(new Sentence(localUse, j++, 2));
                        } else {
                            // x
                            seg.sentences.add(new Sentence(localUse, j++, 2));
                        }
                    }
                    if (tokenList.get(i) == Token.Semicolon) break;
                }

            } else if (curToken == Token.VARIABLE && tokenList.get(i+1) == Token.LeftParentheses) {
                // function ();
                String functionName = valueList.get(i);
                if(functionName.equals(Config.getDisable())) {
                    // disable (   X   );
                    //   i     +1  +2
                    tempUse.add(valueList.get(i+2));
                    seg.sentences.add(new Sentence(tempUse, j++, 0));
                }else if(functionName.equals(Config.getEnable())) {
                    // enable (   X   );
                    //   i     +1  +2
                    tempUse.add(valueList.get(i+2));
                    seg.sentences.add(new Sentence(tempUse, j++, 1));
                }else {
                    // init();
                    tempUse.add(valueList.get(i));
                    seg.sentences.add(new Sentence(tempUse, j++, 11));
                }
                for(;i!=-1;i++)
                    if(tokenList.get(i) == Token.Semicolon)break;

            } else if (curToken == Token.VARIABLE) {
                // x... = y...
                // TODO if the subscript of the array is variable, then it includes one read OP
                for(;i!=-1;i++) {
                    if(tokenList.get(i) == Token.Semicolon)break;
                    tempUse.add(valueList.get(i));
                    tokenUse.add(tokenList.get(i));
                }
                seg.sentences.add(new Sentence(tempUse, j++, 3, tokenUse));

            } else if (tokenList.get(i) == Token.RightBrace) {
                // ... }
                //     i
                if(segString.empty()) {
                    tempUse.add("Segment_END");
                    seg.sentences.add(new Sentence(tempUse, j++, 12));
                    i ++;
                    return;
                }
                int typeOfSegmentEnd = segString.pop();
                String nameOfSegmentEnd = Config.signToName.get(typeOfSegmentEnd);
                tempUse.add(nameOfSegmentEnd + "_END");
                seg.sentences.add(new Sentence(tempUse, j++, typeOfSegmentEnd));
            }
            // -----------------------------
            // TODO highlight the separation
            // -----------------------------
            tempUse = new ArrayList<>();
            if(needLeftBrace > 0) {
                // ) {  or  ) ...
                // i        i
                tempUse.clear();
                String generatedName = Config.signToName.get(needLeftBrace) + "_START";
                tempUse.add(generatedName);
                seg.sentences.add(new Sentence(tempUse, j++, needLeftBrace));
                if(tokenList.get(i+1) == Token.LeftBrace) {
                    segString.push(needLeftBrace);
                }else needRightBrace = needLeftBrace;
                needLeftBrace = 0;
                continue;
            }

            // if(){X} / else {X} / for(){X}
            // X is just one sentence
            if (needRightBrace > 0) {
                tempUse.clear();
                String generatedName = Config.signToName.get(needRightBrace) + "_END";
                tempUse.add(generatedName);
                seg.sentences.add(new Sentence(tempUse, j++, needRightBrace));
                needRightBrace = 0;
            }
        }
    }

    /**
     * Break the loop code segments into sequential code segments
     * @param sg see Segment.java
     * @param globalVariable see Main.java
     * @return abstract line count
     */
    static int refactorForSegmentCode(Segment sg, HashMap<String, Integer> globalVariable) {
        int abstractLineCount = -1;
        boolean ifFor;
        int loopCnt, forStart, forEnd, size = sg.sentences.size(), segCount;

        for (int s = 0; s < size; s ++) {
            int type = sg.sentences.get(s).type;

            forStart = s;
            loopCnt = 1;
            ifFor = (type == 6);
            if(!ifFor && s < size - 1) {

                for(s = forStart + 1; s < size; s ++) {
                    if(sg.sentences.get(s).type == 6 || sg.sentences.get(s).type == 12) {
                        s --;
                        break;
                    }
                }
            }else if(ifFor) {
                Sentence st = sg.sentences.get(s);
                int initialValue = Util.strToInteger(globalVariable, st.varInfo.get(1)),
                        finalValue = Util.strToInteger(globalVariable, st.varInfo.get(2)),
                        delta = (initialValue > finalValue) ? -1 : 1;

                st.type = 13;
                st.varInfo.remove(st.varInfo.size()-1); st.varInfo.add(""+delta);

                loopCnt = Math.abs(initialValue - finalValue);

                for(s = forStart + 1; s < size; s ++) {
                    if(sg.sentences.get(s).type == 7 && sg.sentences.get(s).varInfo.get(0).endsWith("END")) {
                        break;
                    }
                }
            }

            // real
            forEnd = s;
            segCount = forEnd - forStart + 1;
            // abstract
            ArrayList<Integer> loopRecord = new ArrayList<>();
            loopRecord.add(abstractLineCount + 1);      // abstract start line
            loopRecord.add(abstractLineCount + segCount  * loopCnt);   // abstract end line
            loopRecord.add(segCount);    // interval
            loopRecord.add(forStart);    // offset
            sg.flexLines.add(loopRecord);
            abstractLineCount = abstractLineCount + segCount * loopCnt;
        }

        return abstractLineCount + 1;
    }

    /**
     * calculate real line number based the flexLines in segment
     * @param sg see Segment.java
     * @param sC i.e. sentenceCount
     * @return concrete line number
     */
    static int calculateRealSentence(Segment sg, int sC) {
        for (ArrayList<Integer> al : sg.flexLines) {
            int start = al.get(0);
            int end = al.get(1);
            int interval = al.get(2);
            int offset = al.get(3);
            if (start <= sC && sC <= end) {
                if (start == sC)firstLoop = true;
                sC = (sC - start) % interval;
                sC = offset + sC;
                break;
            }
        }
        return sC;
    }

    /**
     * Mock statement execution
     * @param gV i.e. globalVariable
     * @param lV i.e. localVariable
     * @param iC i.e. interControl
     * @param oR i.e. operatorRecord
     * @param ifElseSign store the last if/else condition result(T/F)
     * @param sg i.e. segment
     * @param sC i.e. sentenceCount
     */
    static boolean executeSentence(HashMap<String, Integer> gV, HashMap<String, Integer> lV, HashMap<String, Boolean> iC,
                         HashMap<String, ArrayList<Integer> > oR, Stack<Boolean> ifElseSign, Segment sg, int sC) {
        firstLoop = false;

        sC = calculateRealSentence(sg, sC);
        Sentence st = sg.sentences.get(sC);
        ArrayList<Token> tokenInfo = st.tokenInfo;
        ArrayList<String> varInfo = st.varInfo;
        int type = st.type, priority = sg.priority;

        int size = varInfo.size(), k;
        String updateVariable = "", curValue;
        Token curToken;

        if (size > 0 && varInfo.get(0).contains("_END")) {
            // if / else --->  .... }
            if (varInfo.get(0).startsWith("IF") || varInfo.get(0).startsWith("ELSE")) {
                ifElseSign.pop();
                return false;
            }
        } else {
            // it means the last if/else condition haven't been satisfied
            // so the next sentences won't be executed
            // unless meet the end i.e. code segment above
            if(! ifElseSign.empty() && ! ifElseSign.peek())return false;
        }

        if (type == 0) {
            // disable_isr
            iC.put(varInfo.get(0), true);

        } else if (type == 1) {
            // enable_isr
            iC.put(varInfo.get(0), false);

        } else if (type == 2) {
            // declare
            String localVariable = varInfo.get(0);
            lV.put(localVariable, -1);

        } else if (type == 3) {
            // assignment

            // trivial check
            assert (size == varInfo.size());

            for (k = 0; k < size; k ++) {
                curToken = tokenInfo.get(k);
                curValue = varInfo.get(k);
                if(curToken == Token.Assignment)break;
                if(curToken == Token.VARIABLE) {
                    if (gV.containsKey(curValue)) {
                        ArrayList<Integer> temp = oR.get(curValue);
                        int operatorSign = Util.signForOperator(priority, false);
                        if (temp.size() == 0 || temp.get(temp.size()-1) != operatorSign)
                            oR.get(curValue).add(operatorSign);
                        updateVariable = curValue;
                    }
                }
            }
            // ... = ...
            //     k
            for (; k < size; k ++) {
                curToken = tokenInfo.get(k);
                curValue = varInfo.get(k);
                if(curToken == Token.VARIABLE || curToken == Token.LITERAL) {
                    if (gV.containsKey(curValue)) {
                        ArrayList<Integer> temp = oR.get(curValue);
                        int operatorSign = Util.signForOperator(priority, true);
                        if (temp.size() == 0 || temp.get(temp.size()-1) != operatorSign)
                            oR.get(curValue).add(operatorSign);
                    }
                    // judge if it's LITERAL, if not, it must already in the localVariables
                    lV.put(updateVariable, Util.strToInteger(lV, curValue));
                }
            }

        } else if (type == 4 || type == 5) {
            // if ... else ...
            StringBuilder evalExpression = new StringBuilder();

            for (k = 0; k < size; k ++) {
                curToken = tokenInfo.get(k);
                curValue = varInfo.get(k);
                if (curToken == Token.VARIABLE) {
                    evalExpression.append(Integer.toString(lV.get(curValue)));
                }else evalExpression.append(curValue);
            }
            // generate boolean expression
            // ifElseSign.push(true);
            try {
                boolean result = (Boolean) se.eval(evalExpression.toString());
                ifElseSign.push(result);
            } catch (ScriptException e) {
                e.printStackTrace();
            }

        } else if (type == 13) {
            // For (specific)
            String loopName = varInfo.get(0);
            String initialValue = varInfo.get(1);
            if (firstLoop) {
                lV.put(loopName, Util.strToInteger(initialValue));
            } else {
                int currentValue = lV.get(loopName);
                currentValue = currentValue + Util.strToInteger(varInfo.get(3));
                lV.put(loopName, currentValue);
            }
        }

        return Util.findBug(oR);
    }
    /*
     * type: 0(disable) | 1(enable) | 2(declare) | 3(assignment)
     *       4(if)  | 5(else)
     *       6(for) |
     *       7({ for FOR)  |  8({ for IF)  |  9({ for ELSE) | 10({ for Segment)
     *       11(nothing useful)  |  12(Segment_End)
     *       13(special type for loop sentence)
     */

    /**
     * Find if current operatorRecord status match the bugPattern
     * @param oR i.e. operatorRecord
     * @return true if bug has existed or false
     */
    private static boolean findBug(HashMap<String, ArrayList<Integer> > oR) {

        for(HashMap.Entry<String, ArrayList<Integer> > entry : oR.entrySet()) {
            String keyName  = entry.getKey();
            ArrayList<Integer> val = entry.getValue();
            if (val.size() >= 3) {
                int size = val.size();
                int first = val.get(size-3), second = val.get(size-2), third = val.get(size-1);
                for (int i=0;i<12;i++) {
                    if(first == Config.bugPattern[i][0] &&
                       second == Config.bugPattern[i][1] &&
                       third == Config.bugPattern[i][2]) {
                        // Config.totalBug += 1;
                        int pos = i / 3;
                        if (Config.specificBugInfo.equals("")) {
                            Config.specificBugInfo = keyName + ": " + Config.patternExplain[pos] + " (" +
                                                     first + "->" + second + "->" + third + ")\n";
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
