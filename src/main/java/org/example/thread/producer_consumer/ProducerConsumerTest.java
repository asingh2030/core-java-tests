package org.example.thread.producer_consumer;

import java.util.LinkedList;

public class ProducerConsumerTest {
    static LinkedList<Integer> list = new LinkedList<>();
    final static int size = 2;

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer();
        Thread t1 = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //Start both threads
        t1.start();
        t2.start();

        //Make sure t1 finishes before t2
        t1.join();
        t2.join();

    }

    static class ProducerConsumer {
        public void produce() throws InterruptedException {
            int value = 0;
            while (true) {
                synchronized (this) {
                    while (list.size() == size) {
                        wait();
                    }
                    System.out.println("Producer producing = " + value);
                    list.add(value++);
                    notify();
                    //Added sleep to just easy read output of the program
                    Thread.sleep(1000);
                }
            }
        }

        public void consume() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    while (list.size() == 0) {
                        wait();
                    }
                    System.out.println("Consumer consumes = " + list.removeFirst());
                    notify();
                    //Added sleep to just easy read output of the program
                    Thread.sleep(1000);
                }
            }
        }
    }
}
