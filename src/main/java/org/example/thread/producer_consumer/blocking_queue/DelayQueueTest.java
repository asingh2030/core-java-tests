package org.example.thread.producer_consumer.blocking_queue;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Example to test DelayQueue. Where elements must implement Delayed interface.
 * And while storing elements need to provide delayTime, so when given delayTime will over
 * elements will be available to take/ poll from queue.
 * Delayed interface also extends Comparable interface so elements compareTo method implementation also needed.
 */
public class DelayQueueTest {
    private static class DelayObject implements Delayed{
        private String name;
        private long delayTime;
        public DelayObject(String name, long delayTime) {
            this.name = name;
            this.delayTime = delayTime+System.currentTimeMillis();
        }
        @Override
        public long getDelay(TimeUnit unit) {
            long diff = delayTime - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }
        @Override
        public int compareTo(Delayed o) {
            if(this.delayTime < ((DelayObject)o).getDelayTime()){
                return -1;
            }
            if(this.delayTime > ((DelayObject)o).getDelayTime()){
                return 1;
            }
            return 0;
        }
        public String getName() {
            return name;
        }
        public long getDelayTime() {
            return delayTime;
        }
        @Override
        public String toString() {
            return "DelayObject{" +
                    "name='" + name + '\'' +
                    ", delayTime=" + delayTime +
                    '}';
        }
    }

    public static void main(String[] args) {
        DelayQueue<DelayObject> queue = new DelayQueue<>();
        //Producer
        new Thread(()->{
            Random random = new Random();
            try{
                while (true){
                    System.out.println("Producer is producing delay element...");
                    int delayTime = random.nextInt(1000);
                    DelayObject delayObj = new DelayObject(UUID.randomUUID().toString(),delayTime);
                    queue.add(delayObj);
                    System.out.println("Producer produced delay object "+delayObj);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        //Consumer
        new Thread(()->{
            try{
                while (true){
                    System.out.println("Consumer is waiting to consume...");
                    DelayObject obj = queue.take();
                    System.out.println("Consumer consumed delayObjet - "+obj);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
