package org.example.thread.synchronizers;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Consider a rail bridge on tiny river branch to the train to pass.
 * In that bridge only train can pass at a time.
 * The train can come from both sides, again but only one train can pass that bridge at a time.
 * Lets assume trains are coming from both sides and stops at the rear of bridge, waiting for signal.
 * In this case only one train can pass the signal to avoid collision.
 */
public class SemaphoreTest {
    private static class BridgeCrossingTask implements Runnable{
        private Semaphore signal;
        public BridgeCrossingTask(Semaphore signal) {
            this.signal = signal;
        }
        @Override
        public void run() {
            System.out.println("Train "+Thread.currentThread().getName()+" reached at rail bridge waiting for signal!");
            try {
                signal.acquire();
                System.out.println("Train "+Thread.currentThread().getName()+" got signal and passing bridge that will take 5 seconds!");
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                signal.release();
                System.out.println("Train "+Thread.currentThread().getName()+" pass the bridge and releases the signal!!");
            }
        }
    }
    public static void main(String[] args) {
        Semaphore signal = new Semaphore(1);
        BridgeCrossingTask crossingTask = new BridgeCrossingTask(signal);
        new Thread(crossingTask,"Rajdhani").start();
        new Thread(crossingTask,"Satabdi").start();
    }
}
