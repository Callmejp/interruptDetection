package com.ecnu.tool;

import org.antlr.v4.runtime.tree.ParseTree;


/**
 * 用于运行主程序和抢占程序的“线程”
 */
public class RunSubProgram implements Runnable{
    private int ownPID;
    private ASTEvaluator ae;
    private ParseTree pt;

    RunSubProgram(int rC, ASTEvaluator ae, ParseTree pt) {
        this.ownPID = rC;
        this.ae = ae;
        this.pt = pt;
    }

    @Override
    public void run() {
        ConcurrentControl.lock.lock();
        try {
            // 根本没有机会运行的抢占程序最后都会被唤醒，则直接运行结束即可
            while (ConcurrentControl.pIDOfCurrentProgram != this.ownPID) {
                ConcurrentControl.conditions[ownPID].await();
            }

            ConcurrentControl.hasExecuted[ownPID] = true;
            ae.visit(pt);

            if (! ConcurrentControl.notifyOrder.empty()) {
                // 当某段子程序运行完毕后，
                // 应该找到下一个被唤醒的线程，修改当前运行的进程ID，唤醒线程。
                Integer nextPID = ConcurrentControl.notifyOrder.peek();
                ConcurrentControl.notifyOrder.pop();
                ConcurrentControl.pIDOfCurrentProgram = nextPID;
                // System.out.println("线程 " + ownPID + "执行完毕，准备唤醒线程 " + nextPID);
                ConcurrentControl.conditions[nextPID].signal();
            }

            if (ownPID == 0) {
                // System.out.println("主程序运行完毕");
                ConcurrentControl.mainProgramEnd = true;
            }
            // 最后唤醒没有运行的线程，得让它们都滚蛋
            // 不过是依次唤醒下一个，确保顺序；并且主程序已执行完毕
            for (int i = ownPID+1; i < Config.segCount; i++) {
                if (! ConcurrentControl.hasExecuted[i] && ConcurrentControl.mainProgramEnd) {
                    ConcurrentControl.pIDOfCurrentProgram = i;
                    ConcurrentControl.conditions[i].signal();
                    break;
                }
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            ConcurrentControl.lock.unlock();
        }
    }
}
