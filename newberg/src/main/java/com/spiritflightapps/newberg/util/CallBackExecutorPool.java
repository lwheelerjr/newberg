package com.spiritflightapps.newberg.util;


import com.spiritflightapps.newberg.ICallBack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallBackExecutorPool {

    private ExecutorService executorService;
    private ICallBack finishedCallBack;
    private int executorLiveThreadCount = 0;

    public CallBackExecutorPool(int size, ICallBack finishedCallBack) {
        executorService=Executors.newFixedThreadPool(size);
        this.finishedCallBack = finishedCallBack;
    }

    public void execute(Runnable runnable) {
        ++executorLiveThreadCount;
        executorService.execute(new RunnableWrapper(runnable));
    }

    private class RunnableWrapper implements Runnable {
        Runnable runnable;

        public RunnableWrapper(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            runnable.run();
            executorLiveThreadCount--;
            if (executorLiveThreadCount == 0) {
                finishedCallBack.callBack();
            }
        }
    }

}
