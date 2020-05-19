package com.ecnu.tool;


/**
 * class that hold the CONSTANTS
 */
final class Config {
    static final String directoryName = "TestCase";
    static final String fileName = "svp_simple_003_001.c";
    static final int segCount = 3;
    static final String openInterruptName = "enable_isr";
    static final String closeInterruptName = "disable_isr";

    static final String[] subProgramName = {
        "svp_simple_001_001_main",
        "svp_simple_001_001_isr_1",
        "svp_simple_001_001_isr_2"
    };


    static final String[] patternDescribe = {
            "R W R",
            "W W R",
            "W R W",
            "R W W"
    };
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


}
