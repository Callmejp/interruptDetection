package com.ecnu.tool;



class RepeatLockLog {
    // 此处的变量也是会被多线程改动，所以volatile是必须的
    static volatile int[] lockState = new int[5];


    static void initRepeatLockLog() {
        lockState[2] = 0;
        lockState[1] = 0;
    }

    /**
     * 更新锁的自动机状态
     *      1   0
     * 0    1
     * 1    2   0
     * 2        3
     * 3  wrong state
     * @param lockID 哪个锁
     * @param action 加锁（1）或去锁（0）
     */
    static void updateState(int lockID, int action) {
        if (lockState[lockID] == 0) {
            if (action == 1)lockState[lockID] = 1;

        } else if (lockState[lockID] == 1) {
            if (action == 0)lockState[lockID] = 0;
            else lockState[lockID] = 2;

        } else if (lockState[lockID] == 2) {
            if (action == 0)lockState[lockID] = 3;

        }
    }


}
