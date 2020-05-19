package com.ecnu.tool;

import java.util.HashMap;

import com.ecnu.auxiliary.Symbol;
import org.antlr.v4.runtime.tree.ParseTree;


/**
 * 记录准备信息的类：
 * 其中ast是整体程序语法树的根节点；
 * rootOfSubProgram记录各子程序语法树的“根节点”；
 * lineOfSubProgram记录各子程序的整体代码行数；
 * MainScope记录全局变量及初始化信息。
 */
class AnnotatedTree {
    static ParseTree ast;
    static HashMap<String, ParseTree> rootOfSubProgram = new HashMap<>();
    static HashMap<String, Integer> lineOfSubProgram = new HashMap<>();
    static HashMap<String, Symbol> MainScope = new HashMap<>();

    AnnotatedTree() { }

    /*
    TODO: 因为这里的varName可能是一般表达式，所以此处写法不够一般性
     */
    static Integer strToInteger(String varName) {
        Symbol temp;
        if ((temp = MainScope.get(varName)) == null) {
            if (varName.contains("0x")) {
                return Integer.parseInt(varName.substring(2), 16);
            }else return Integer.parseInt(varName, 10);

        }else {
            return temp.getValue();
        }
    }

}
