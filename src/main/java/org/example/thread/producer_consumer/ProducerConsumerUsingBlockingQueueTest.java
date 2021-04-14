package org.example.thread.producer_consumer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

public class ProducerConsumerUsingBlockingQueueTest {
    static final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0,99).forEach(n->queue.add(n));
                }
        );
        Thread consumer = new Thread(() -> {
            //If use queue.remove() method that will throw NoSuchElementException
            //Because We used non blocking removed method
//            IntStream.range(0,99).forEach(n->System.out.println("Consume = "+queue.remove()));

            //So Instead of remove we need to use queue.take() method
            //q.take() to consume from the queue. If the queue is empty,
            //this will block until there is something for the consumer thread to fetch back.
            IntStream.range(0,99).forEach(n-> {
                try {
                    System.out.println("Consume = "+queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        producer.start();

        System.out.println("Size of queue = "+queue.size());

        consumer.start();

        producer.join();
        consumer.join();
    }
}
