package com.ecnu.tool;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 所有用于协调运行主程序和抢占程序的线程运行的变量都存储在这个类中
 */
class ConcurrentControl {
    // 因为是“伪并发”，所以以下变量不需要加锁，只需要保证可见性
    static volatile int[] interHappenPlace = {-1, -1, -1};
    // true 表示可以抢占 false 表示被屏蔽
    static volatile boolean[] interControl = {true, true, true};
    // 是否进入程序段，否则需要依次执行
    static volatile boolean[] hasExecuted = {false, false, false};
    // 当前运行的子程序号
    static volatile int pIDOfCurrentProgram = 0;
    // 唤醒顺序的栈
    static volatile Stack<Integer> notifyOrder = new Stack<>();
    // 主程序是否执行完毕
    static volatile boolean mainProgramEnd = false;

    final static ReentrantLock lock = new ReentrantLock();
    final static Condition[] conditions = {
            lock.newCondition(), lock.newCondition(), lock.newCondition()
    };


    static void initConcurrentControl(int inter_1, int inter_2) {
        // 分别记录中断1和中断2发生的位置
        ConcurrentControl.interHappenPlace[1] = inter_1;
        ConcurrentControl.interHappenPlace[2] = inter_2;
        // 重置当前运行的子程序段号为0，即主程序先运行
        ConcurrentControl.pIDOfCurrentProgram = 0;
        ConcurrentControl.mainProgramEnd = false;
        // 清空栈
        ConcurrentControl.notifyOrder.clear();

        for (int i=0; i<Config.segCount; i ++) {
            ConcurrentControl.interControl[i] = true;
            ConcurrentControl.hasExecuted[i] = false;
        }

    }
}
