package com.ecnu.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


class VisitPatternLog {
    // 此处的变量也是会被多线程改动，所以volatile是必须的
    static volatile ArrayList<HashMap<String, Boolean> > writeRecord;
    static volatile ArrayList<HashMap<String, Boolean> > readRecord;
    // 在一次枚举中是否已经找到一个Bug了
    static volatile boolean hasFoundBug;
    // Bug信息缓存
    static volatile StringBuilder sb = new StringBuilder();
    static volatile String placeOfInter_1;
    static volatile String placeOfInter_2;



    @SuppressWarnings("unchecked")
    static void initVisitPatternLog() {
        hasFoundBug = false;

        HashMap<String, Boolean> temp = new HashMap<>();
        for (String variableName : AnnotatedTree.MainScope.keySet()) {
            temp.put(variableName, false);
        }

        writeRecord = new ArrayList<>();
        readRecord = new ArrayList<>();
        for (int i=0; i<Config.segCount; i++) {
            writeRecord.add((HashMap<String, Boolean>)temp.clone());
            readRecord.add((HashMap<String, Boolean>)temp.clone());
        }

        sb.setLength(0);
        placeOfInter_1 = "";
        placeOfInter_2 = "";
    }

    static void logSpecificInfo(int pID, String variableName, int patternId) {
        VisitPatternLog.hasFoundBug = true;
        sb.append("其中对于变量 ").append(variableName).append(" 在程序段").append(pID).append("发生了 ").append(Config.patternDescribe[patternId]).append(" 访问序\n");
        // sb.append("中断发生情况:");
        // sb.append("中断1发生在第 ").append(ConcurrentControl.interHappenPlace[1]).append(" 行\n");
        // sb.append("中断2发生在第 ").append(ConcurrentControl.interHappenPlace[2]).append(" 行\n");
        // System.out.println(sb.toString());
    }


    static void logInterHappenPlace(int pID, int interID, String codeText) {
        if (interID == 2) {
            placeOfInter_2 = "中断2发生在程序段" + pID + "的 " + codeText + " 前";
        } else if (interID == 1) {
            placeOfInter_1 = "中断1发生在程序段" + pID + "的 " + codeText + " 前";
        }
    }
}
