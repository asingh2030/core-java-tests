package org.example.thread.producer_consumer.blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Test class for @{@link java.util.concurrent.ArrayBlockingQueue}
 * Ordered implementation of producer-consumer pattern.
 * Producer produces integer value and consumer consumes it when producer produced.
 */
public class ArrayBlockingQueueTest {
    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        //Producer thread
        new Thread(() -> {
            int i = 0;
            try {
                while (true) {
                    queue.put(++i);
                    System.out.println("Added - "+i);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        //Consumer thread
        new Thread(()->{
            try{
                while (true){
                    Integer i = queue.take();
                    System.out.println("Polled - "+i);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
