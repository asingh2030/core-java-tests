package org.example.thread.future;

import java.util.concurrent.*;

/**
 * Create two task. After one is completely executed, then after waiting 2000 millisecond,
 * second task is being executed.
 */
public class FutureTaskTest {
    public static void main(String[] args) {
        MyRunnable runnable1 = new MyRunnable(1000);
        MyRunnable runnable2 = new MyRunnable(2000);

        FutureTask<String> futureTask1 = new FutureTask<>(runnable1, "FutureTask1 is completed!");
        FutureTask<String> futureTask2 = new FutureTask<>(runnable2, "FutureTask2 is completed!");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(futureTask1);
        executorService.submit(futureTask2);

        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    System.out.println("Both submitted future tasks completed!");
                    executorService.shutdown();
                    return;
                }
                if (futureTask1.isDone()) {
                    System.out.println("FutureTask1 - " + futureTask1.get());
                }
                System.out.println("Waiting for FutureTask2 to complete!");
                String futureTaskResult2 = futureTask2.get(250, TimeUnit.MILLISECONDS);
                if(futureTaskResult2 != null){
                    System.out.println("FutureTask2 result "+futureTaskResult2);
                }
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error in FutureTask1 - " + e.getLocalizedMessage());
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("Timeout error in future task2 to complete!"+e.getLocalizedMessage());
            }
        }
    }

    private static class MyRunnable implements Runnable{
        private final long waitTime;

        public MyRunnable(long waitTime){
            this.waitTime = waitTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(waitTime);
                System.out.println("Name of thread - "+Thread.currentThread().getName());

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
