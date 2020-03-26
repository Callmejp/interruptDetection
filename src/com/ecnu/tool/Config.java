package com.ecnu.tool;


import java.util.HashMap;

/**
 * class that hold the CONSTANTS
 */
final class Config {
    private static final String directoryName = "TestCase";
    private static final String fileName = "test.c";
    private static final int segCount = 3;

    private static final String disable = "disable_isr";
    private static final String enable = "enable_isr";

    private static final String signForStart = "FOR_START";
    private static final String signForEnd = "FOR_END";
    private static final String signIfStart = "IF_START";
    private static final String signIfEnd = "IF_END";

    private static final int signForFor = 7;
    private static final int signForIf = 8;
    private static final int signForElse = 9;

     /*
              R    W
    MAIN:     0    1
    SUB_1:    2    3
    SUB_2:    4    5
    RWR; WWR; RWW; WRW
    */
    static final int[][] bugPattern = new int[][]{
             {0, 3, 0}, {0, 5, 0}, {2, 5, 2},
             {1, 3, 0}, {1, 5, 0}, {3, 5, 2},
             {0, 3, 1}, {0, 5, 1}, {2, 5, 3},
             {1, 2 ,1}, {1, 4 ,1}, {3, 4, 3}
    };
    static final String[] patternExplain = new String[]{"R W R", "W W R", "R W W", "W R W"};
    static int totalBug = 0;
    static String specificBugInfo;

    static final HashMap<Integer, String> signToName;

    static {
        signToName = new HashMap<>();
        signToName.put(0, "disable");
        signToName.put(1, "enable");
        signToName.put(2, "declare");
        signToName.put(3, "assignment");
        signToName.put(4, "if");
        signToName.put(5, "else");
        signToName.put(6, "for");
        signToName.put(Config.getSignForFor(), "FOR");
        signToName.put(Config.getSignForIf(), "IF");
        signToName.put(Config.getSignForElse(), "ELSE");
        signToName.put(11, "nothing");
        signToName.put(12, "segment_end");
    }

    static String getDisable() {
        return disable;
    }

    static String getEnable() {
        return enable;
    }

    static int getSignForFor() {
        return signForFor;
    }

    static int getSignForIf() {
        return signForIf;
    }

    static int getSignForElse() {
        return signForElse;
    }

    static String getDirectoryName() {
        return directoryName;
    }

    static String getFileName() {
        return fileName;
    }

    static int getSegCount() {
        return segCount;
    }

    static String getSignForStart() {
        return signForStart;
    }

    static String getSignForEnd() {
        return signForEnd;
    }

     static String getSignIfStart() {
        return signIfStart;
    }

     static String getSignIfEnd() {
        return signIfEnd;
    }
}
