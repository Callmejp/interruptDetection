package com.ecnu.tool;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Callmejp
 */
public class Main {

    private static HashMap<String, Integer> globalVariable = new HashMap<>();
    private static ArrayList<Token> tokenList = new ArrayList<>();
    private static ArrayList<String> valueList = new ArrayList<>();
    private static ArrayList<Segment> program = new ArrayList<>();
    private static ArrayList<Integer> lineCount = new ArrayList<>();


    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        StringBuilder codeBuffer = new StringBuilder();

        String sourceFileName = Config.getDirectoryName() + File.separator + Config.getFileName();
        // System.out.println(sourceFileName);
        File sourceFile = new File(sourceFileName);

        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), StandardCharsets.UTF_8))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                // delete the comments & head files
                if(line.contains("#include") || line.contains("*"))continue;
                codeBuffer.append(line);
                codeBuffer.append(" ");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Lexical analysis function call
        String codeContent = codeBuffer.toString();
        Parser.parseCode(codeContent, tokenList, valueList);

//        for(int i=0;i<tokenList.size();i++) {
//            System.out.println(tokenList.get(i) + ": " + valueList.get(i));
//        }

        // handle the Global Variables, via '-1' record the ones without initialization
        // save the 'i' to continue to use
        int i = 0;
        String lastVariable = "";
        for(;i<tokenList.size();i++) {
            Token temp = tokenList.get(i);
            if(temp == Token.VOID)break;
            if(temp == Token.VARIABLE) {
                lastVariable = valueList.get(i);
                globalVariable.put(lastVariable, -1);
                // skip the dimension of arrays
                if(tokenList.get(i+1) == Token.LeftBrackets)i += 3;
            }else if(temp == Token.LITERAL) {
                globalVariable.put(lastVariable, Util.strToInteger(valueList.get(i)));
            }
        }

//        globalVariable.forEach((key, val) ->
//            System.out.println(key + ": " + val)
//        );

        // Refactor character stream to sentences
        for(int segCount=0; segCount<Config.getSegCount(); segCount++) {
            Segment curSegment = new Segment();
            if(segCount > 0)Util.reset();
            else Util.reset(i);
            Util.recurHandleCode(tokenList, valueList, curSegment);
            curSegment.priority = segCount;
            program.add(curSegment);
        }

        for(Segment sg : program) {
            System.out.print(sg);
            System.out.println("-------------------------------");
        }



        for(Segment sg : program) {
            lineCount.add(Util.refactorForSegmentCode(sg, globalVariable));
        }

        /*
         TODO
         INTER1: 0 ~ i + 1
            INTER2: 0 ~ i + j + 2
            if(INTER1 == 0 && INTER2 > i + 1) continue

            // initialize all the Maps, Stacks...
            -------------------------------------
            // keep pushing RX/WX(X=0/1/2) to visitRecord
            // Once length >= 3 Judge if pattern exist

            For sentenceCnt(0 ~ i+1) in MAIN:
                if(INTER2 == sentenceCnt && !disable(2)) {
                    Execute the Sub_2
                }else if(INTER1 == sentenceCnt && !disable(1)) {
                    For sentenceOfSub_1 in Sub_1:
                        if(INTER2 - i == sentenceOfSub_1 && !disable(2)) {
                            Execute the Sub_2
                        }
                }
        */
        int MAIN_COUNT = lineCount.get(0), SUB1_COUNT = lineCount.get(1), SUB2_COUNT = lineCount.get(2);
        boolean hasBug;
        StringBuilder sb = new StringBuilder();

        for (int inter_1 = -1; inter_1 < MAIN_COUNT; inter_1 ++) {
            for (int inter_2 = -1; inter_2 < (MAIN_COUNT + SUB1_COUNT); inter_2 ++) {

                // INTER_1 won't happen, so INTER_2 can't grab INTER_1
                if(inter_1 == -1 && inter_2 >= MAIN_COUNT)continue;
                // Initialize all the maps that store the variables
                HashMap<String, Integer> localVariable_MAIN = (HashMap<String, Integer>) globalVariable.clone();
                HashMap<String, Integer> localVariable_SUB1 = (HashMap<String, Integer>) globalVariable.clone();
                HashMap<String, Integer> localVariable_SUB2 = (HashMap<String, Integer>) globalVariable.clone();

                HashMap<String, Boolean> interControl = new HashMap<>();
                interControl.put("1", false); interControl.put("2", false);

                Stack<Boolean> ifElseSign_MAIN = new Stack<>();
                Stack<Boolean> ifElseSign_SUB1 = new Stack<>();
                Stack<Boolean> ifElseSign_SUB2 = new Stack<>();
                /*
                          R    W
                MAIN:     0    1
                SUB_1:    2    3
                SUB_2:    4    5
                */
                HashMap<String, ArrayList<Integer> > operatorRecord = new HashMap<>();
                for(HashMap.Entry<String, Integer> entry : globalVariable.entrySet()) {
                    ArrayList<Integer> t = new ArrayList<>();
                    operatorRecord.put(entry.getKey(), t);
                }

                hasBug = false;
                Config.specificBugInfo = "";

                for (i = 0; i < MAIN_COUNT; i++) {
                    if (inter_2 == i && ! interControl.get("2")) {
                        for (int k = 0; k < SUB2_COUNT; k ++) {
                            hasBug = hasBug || Util.executeSentence(globalVariable, localVariable_SUB2,
                                    interControl, operatorRecord, ifElseSign_SUB2, program.get(2), k);
                        }
                    }
                    if (inter_1 == i && ! interControl.get("1")) {
                        for (int j = 0; j < SUB1_COUNT; j++) {
                            if (inter_2 == j + MAIN_COUNT && ! interControl.get("2")) {
                                for (int k = 0; k < SUB2_COUNT; k ++) {
                                    hasBug = hasBug || Util.executeSentence(globalVariable, localVariable_SUB2,
                                            interControl, operatorRecord, ifElseSign_SUB2, program.get(2), k);
                                }
                            }

                            hasBug = hasBug || Util.executeSentence(globalVariable, localVariable_SUB1,
                                    interControl, operatorRecord, ifElseSign_SUB1, program.get(1), j);

                        }
                    }

                    hasBug = hasBug || Util.executeSentence(globalVariable, localVariable_MAIN, interControl,
                            operatorRecord, ifElseSign_MAIN, program.get(0), i);
                }

                if (hasBug) {
                    Config.totalBug += 1;
                    int happenPlace;
                    sb.append("Find Bug:\n");
                    if (inter_1 == -1) {
                        sb.append("INTER_1 doesn't happen\n");
                    } else {
                        happenPlace = Util.calculateRealSentence(program.get(0), inter_1);
                        sb.append("INTER_1 happens in Line ").append(happenPlace).append(" in MAIN PROGRAM\n");
                    }

                    if (inter_2 == -1) {
                        sb.append("INTER_2 doesn't happen\n");
                    } else if (inter_2 < MAIN_COUNT) {
                        happenPlace = Util.calculateRealSentence(program.get(0), inter_2);
                        sb.append("INTER_2 happens in Line ").append(happenPlace).append(" in MAIN PROGRAM\n");
                    } else {
                        happenPlace = Util.calculateRealSentence(program.get(1), inter_2 - MAIN_COUNT);
                        sb.append("INTER_2 happens in Line ").append(happenPlace).append(" in SUB_1 PROGRAM\n");
                    }
                    sb.append(Config.specificBugInfo);
                    sb.append("---------------------\n");
                }
            }
        }

        System.out.println(sb.toString());
        System.out.println("There are in all " + Config.totalBug + " Bugs.");
    }
}
