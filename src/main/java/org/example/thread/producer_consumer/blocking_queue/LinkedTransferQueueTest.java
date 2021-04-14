package org.example.thread.producer_consumer.blocking_queue;
import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
/**
 * Example to put and take elements from LinkedTransferQueue using blocking insertions and retrieval.
 * Producer thread will wait until there is consumer ready to take the item from the queue.
 * Consumer thread will wait if queue is empty. As soon as, there is a single element in queue, it take out the element. Only after consumer has taken the message, producer can another message.
 */
public class LinkedTransferQueueTest {
    public static void main(String[] args) {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        //Producer
        new Thread(()->{
            Random random = new Random(1);
            try{
                while(true){
                    System.out.println("Producer is waiting to transfer item...");
                    int i = random.nextInt();
                    boolean added = queue.tryTransfer(i);
                    if(added){
                        System.out.println("Producer added the element - "+i);
                    }
                    Thread.sleep(TimeUnit.SECONDS.toMillis(3));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        //Consumer
        new Thread(()->{
            try{
                while (true){
                    System.out.println("Consumer is waiting to take the element...");
                    Integer i = queue.take();
                    System.out.println("Consumer received the element - "+i);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(3));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
