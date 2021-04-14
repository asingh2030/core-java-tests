package org.example.thread.producer_consumer.blocking_queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueTest {
    //Producer
    private static class Producer implements Runnable{
        private BlockingQueue<Integer> queue;

        public Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("Producer is waiting to produce element...");
            try{
                for (int i=0;i<50;i++){
                    Integer number = produce();
                    queue.put(number);
                    System.out.println("Producer produced - "+number);
                }
                queue.put(-1);//Indicating end of producing
                System.out.println("Producer stopped!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        private Integer produce() {
            Random random = new Random();
            int i = random.nextInt(1000);
            try {
                //Fake produce time
                Thread.sleep(TimeUnit.MILLISECONDS.toMillis(random.nextInt(1000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        }
    }
    //Consumer
    private static class Consumer implements Runnable{
        private BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            try{
                while (true){
                    System.out.println("Consumer is waiting to take number...");
                    Integer i = queue.take();
                    if(i == -1){
                        System.out.println("Consumer Stopped!");
                        break;
                    }
                    consume(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        private void consume(Integer i) {
            //fake consuming delay
            Random random = new Random();
            try {
                System.out.println("Consumer consumed - "+i);
                Thread.sleep(TimeUnit.MILLISECONDS.toMillis(random.nextInt(1000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }
}
