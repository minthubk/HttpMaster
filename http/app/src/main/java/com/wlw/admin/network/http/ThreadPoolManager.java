package com.wlw.admin.network.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    //1、把任务添加到请求队列中
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    //添加任务
    public void execute(Runnable runnable) {
        if (runnable != null) {
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //2 、把队列中的任务放入到线程池
    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadPoolManager() {
        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    queue.put(r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        threadPoolExecutor = new ThreadPoolExecutor(4, 20, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), rejectedExecutionHandler);

        Runnable runnable = () -> {
            while (true) {
                Runnable take = null;
                try {
                    take = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (take != null) {
                    threadPoolExecutor.execute(take);
                }
            }
        };
        threadPoolExecutor.execute(runnable);
    }

    private static ThreadPoolManager ourInstance;

    public static ThreadPoolManager getOurInstance() {
        if (ourInstance == null) {
            ourInstance = new ThreadPoolManager();
        }
        return ourInstance;
    }
}
