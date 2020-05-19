package com.ecnu.tool;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.ecnu.grammer.CParser;
import com.ecnu.grammer.CLexer;

import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * compile方法主要完成运行程序前的准备工作
 * execute方法以“多线程”模拟抢占程序和主程序的运行情况，但实际只会有一个线程运行
 */
class Compiler {

    void compile(String script) {
        // 词法分析
        CLexer lexer = new CLexer(CharStreams.fromString(script));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // 语法分析
        CParser parser = new CParser(tokens);
        // 初始化自定义语法树
        AnnotatedTree.ast = parser.prog();

        // 多步的语义分析：准备工作
        ParseTreeWalker walker = new ParseTreeWalker();

        // pass1: 全局变量
        System.out.println("Pass 1:");
        GlobalVariableScanner pass1 = new GlobalVariableScanner();
        walker.walk(pass1, AnnotatedTree.ast);

        // pass2: 统计各程序段行数
        System.out.println("Pass 2:");
        LineCountScanner pass2 = new LineCountScanner();
        walker.walk(pass2, AnnotatedTree.ast);

    }



    void execute() {
        ExecutorService es = new ThreadPoolExecutor(Config.segCount, Config.segCount,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        // 子程序2既可以抢占主程序还可以抢占子程序1，所以secondInterHappen范围更大
        int firstInterHappen = AnnotatedTree.lineOfSubProgram.get(Config.subProgramName[0]);
        int secondInterHappen = firstInterHappen + AnnotatedTree.lineOfSubProgram.get(Config.subProgramName[1]);
        // 枚举抢占的发生点
        for (int inter_1 = 0; inter_1 < firstInterHappen; inter_1++) {
            for (int inter_2 = 0; inter_2 < secondInterHappen; inter_2++ ) {
                // System.out.println("中断: " + inter_1 + " " + inter_2);
                // 不考虑两抢占程序同时发生的情况，也是为了方便之后的线程唤醒。
                if (inter_1 == inter_2) continue;

                // 初始化ConcurrentControl
                ConcurrentControl.initConcurrentControl(inter_1, inter_2);
                // 初始化ResultLog
                ResultLog.initResultLog();

                // 准备装载返回线程结果的Future数组
                ArrayList<Future> rst = new ArrayList<>();
                // 启动线程，其中让主线程最后启动
                // 表示当前运行的程序行数
                int startLineCount = secondInterHappen + AnnotatedTree.lineOfSubProgram.get(Config.subProgramName[2]);
                for (int i=Config.segCount-1; i>=0; i--) {
                    // 不断减去程序行数
                    startLineCount -= AnnotatedTree.lineOfSubProgram.get(Config.subProgramName[i]);
                    // System.out.println("线程 " + i + " 的起始行数为 " + startLineCount);
                    ParseTree pt = AnnotatedTree.rootOfSubProgram.get(Config.subProgramName[i]);
                    ASTEvaluator visitor = new ASTEvaluator(i, startLineCount);
                    rst.add(es.submit(new RunSubProgram(i, visitor, pt)));
                }
                // 等待结束并得到结果
                for (int i=0;i<Config.segCount;i++) {
                    try {
                        rst.get(i).get();
                    } catch (ExecutionException | InterruptedException | CancellationException ie) {
                        ie.printStackTrace();
                    }
                }

            }
        }
        es.shutdown();
    }

}
