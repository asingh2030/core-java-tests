package org.example.thread.synchronizers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * We consider example as cricket match, where Bawler will wait till both umpires & batsman and
 * all fielders are not reached to their respective positions.
 * Then n only Bawler can start bawling.
 * In note we can say that even if umpires and 10 fielders reached their positions,
 * bawler still need to wait for 11th fielder to reach his position.
 * Once 11th fielder reaches his position and call await method then bawler can allow to ball.
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        //Total 15 peoples (2 Umpires + 2 batsman, 10 fielders + 1 bowler) need to set their respective position
        // before bawler start balling.
        CyclicBarrier barrier = new CyclicBarrier(15);
        System.out.println("Positioning Umpire...");
        for (int i=1;i<=2;i++){
            new Thread(new Umpire(barrier, "Umpire"+i)).start();
        }
        System.out.println("Umpires thread started!");
        System.out.println("Positioning Fielders...");
        for (int i=1;i<=10;i++){
            new Thread(new Fielder(barrier, "Fielder"+i)).start();
        }
        System.out.println("Fielders thread started!");
        System.out.println("Positioning Batsman...");
        for (int i=1;i<=2;i++){
            new Thread(new Fielder(barrier, "Batsman"+i)).start();
        }
        System.out.println("Batsman thread started!");
        try {
            System.out.println("Positioning bawler....");
            barrier.await();//This await is for bawler to complete the barrier position.
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("Players and umpires set to their respective positions, now bawler can ball!!!!");
    }
    private static class Umpire implements Runnable{
        private CyclicBarrier barrier;
        private String name;
        public Umpire(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println(name+" moving to position!");
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                System.out.println(name+" set to his position!!");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
    private static class Fielder implements Runnable{
        private CyclicBarrier barrier;
        private String name;
        public Fielder(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println(name+" moving to position!");
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                System.out.println(name+" set to his position!!");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
    private static class Batsman implements Runnable{
        private CyclicBarrier barrier;
        private String name;
        public Batsman(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println(name+" moving to position!");
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                System.out.println(name+" set to his position!!");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
