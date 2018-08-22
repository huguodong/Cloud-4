package com.ssitcloud.app_reader.task;

import java.util.TimerTask;

/**
 * Created by LXP on 2017/4/17.
 * 安全任务，保证run在阻塞中不重复执行
 */

public abstract class AbstractSafeTask extends TimerTask {
    private volatile int state = 0;

    @Override
    public void run() {
        if (state == 0) {
            try {
                state = 1;
                task();
            } finally {
                state = 0;
            }
        }
    }

    public abstract void task();
}
