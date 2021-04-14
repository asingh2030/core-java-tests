package org.example.thread.producer_consumer.blocking_queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Producer-Consumer pattern example with SynchronousQueue.
 * SynchronousQueue is designed to hand off objects from a producer thread to consumer threads synchronously:
 * when the producer thread puts an object to the queue, it must wait for a corresponding take from a consumer thread,
 * when the consumer thread wants to take element from the queue, it must wait for a corresponding put from the producer thread.
 * SynchronousQueue has no capacity, its act like a empty collection.
 */
public class SynchronousQueueTest {
    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        //Producer
        new Thread(() -> {
            Random random = new Random();
            try {
                while (true) {
                    int i = random.nextInt(1000);
                    System.out.println("Producer created number - " + i);
                    queue.put(i);
                    Thread.sleep(TimeUnit.MILLISECONDS.toMillis(random.nextInt(1000)));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Stream.of(10).forEach(i-> new Thread(new Consumer("Consumer"+i, queue)).start());
    }
    //Consumer
    private static class Consumer implements Runnable {
        String name;
        BlockingQueue<Integer> queue;
        public Consumer(String name, BlockingQueue<Integer> queue) {
            this.name = name;
            this.queue = queue;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    Integer integer = queue.take();
                    System.out.println(this.name+" consumed - " + integer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
