package org.example.thread.synchronizers;

import java.util.concurrent.Exchanger;

/**
 * Producer-Consumer pattern where producer thread produces data and exchange with consumer thread
 * in synchronous manner at exchange point.
 */
public class ExchangerTest {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger();
        new Thread(()->{
            try {
                String data = exchanger.exchange("Produced data!");
                System.out.println(Thread.currentThread().getName()+" exchanged data with consumer. Data received = "+data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Producer").start();
        new Thread(()->{
            try {
                String data = exchanger.exchange("Consumed data!");
                System.out.println(Thread.currentThread().getName()+" exchanged data with producer. Data received = "+data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consumer").start();
    }
}
