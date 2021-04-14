package org.example.thread.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsTest {

    public static void main(String[] args) {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
//        scheduledThreadPool.scheduleAtFixedRate(command, initialDelay, period, TimeUnit.MILLISECONDS);
//        scheduledThreadPool.scheduleWithFixedDelay(command, initialDelay, delay, TimeUnit.MILLISECONDS);


    }
}
