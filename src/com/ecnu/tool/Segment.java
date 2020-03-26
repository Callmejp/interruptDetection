package com.ecnu.tool;

import java.util.ArrayList;

/**
 * Code Segment include:
 *
 *  priority: 0/1/2 -> MAIN/SUB_1/SUB_2
 * sentences: executable sentence
 * flexLines: key params that help translate the abstract line number into real line number
 */
class Segment {

    int priority = 0;
    ArrayList<Sentence> sentences;
    ArrayList<ArrayList<Integer> > flexLines;

    Segment() {
        this.sentences = new ArrayList<>();
        this.flexLines = new ArrayList<>();
    }

    private int sentenceCount() {
        int len = sentences.size();
        if(len == 0)return 0;
        return sentences.get(len - 1).sentenceId + 1;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("The Code Segment has ").append(this.sentenceCount()).append(" lines\n");
        sb.append("----------------------------------\n");
        for(Sentence st : sentences) {
            sb.append("Line ").append(st.sentenceId).append(" (").append(Config.signToName.get(st.type)).append(") : ");
            for(String str : st.varInfo) {
                sb.append(str).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

/**
 * tokenInfo: VOID/VARIABLE/LITERAL
 * varInfo:  "void"/"reader1"/10000
 * sentenceId:  the line number
 * type: see Util.java
 */
class Sentence {
    ArrayList<String> varInfo;
    ArrayList<Token> tokenInfo;
    int sentenceId;
    int type;


    Sentence(ArrayList<String> varInfo, int sentenceId, int type) {
        this.varInfo = varInfo;
        this.sentenceId = sentenceId;
        this.type = type;
        this.tokenInfo = new ArrayList<>();
    }

    Sentence(ArrayList<String> varInfo, int sentenceId, int type, ArrayList<Token> tokenInfo) {
        this.varInfo = varInfo;
        this.sentenceId = sentenceId;
        this.type = type;
        this.tokenInfo = tokenInfo;
    }

}
