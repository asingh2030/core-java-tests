package org.example.thread;

import java.util.concurrent.*;

public class ThreadCreationTest {

    private static volatile Integer count = 0;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new RunnableThread());
        Future<Integer> callableResultFuture = executorService.submit(new CallableThread());
        try {
            Integer integer = callableResultFuture.get();
            System.out.println("Callable result count = "+integer);
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    public static class RunnableThread implements Runnable{
        @Override
        public void run() {
            System.out.println("Runnable thread count = "+count++);
        }
    }

    public static class CallableThread implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            return count++;
        }
    }
}
