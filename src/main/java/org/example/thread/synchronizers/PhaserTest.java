package org.example.thread.synchronizers;

import java.util.concurrent.*;

/**
 * By using Phaser we can perform synchronization of threads in different phases and will register threads at any point of phases.
 * In this example will process multiple thread in 3 different phases.
 * Let's say that we want to coordinate multiple phases of actions.
 * Three threads will process the first phase, and two threads will process the second phase.
 */
public class PhaserTest {
   public static class LongRunningAction implements Runnable{
       private String name;
       private Phaser phaser;
       public LongRunningAction(String name, Phaser phaser) {
           this.name = name;
           this.phaser = phaser;
           //By using register() method it will increment the number of threads using that specific Phaser.
           this.phaser.register();
       }
       @Override
       public void run() {
           //The call to the arriveAndAwaitAdvance() will cause the current thread to wait on the barrier.
           //when the number of arrived parties becomes the same as the number of registered parties, the execution will continue.
           System.out.println(name+" in phase "+phaser.getPhase());
           phaser.arriveAndAwaitAdvance();

           try {
               Thread.sleep(TimeUnit.SECONDS.toMillis(1));
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           //After the processing is done, the current thread is de-registering itself by calling the arriveAndDeregister() method.
           phaser.arriveAndDeregister();
       }
   }
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("Phaser in must be in phase 0. actual phase = "+phaser.getPhase());//this position phase is must be 0.
        //Now will create 3 threads which will be waiting on the barrier until we will be call the arriveAndAwaitAdvance()
        executorService.submit(new LongRunningAction("thread-1", phaser));
        executorService.submit(new LongRunningAction("thread-2", phaser));
        executorService.submit(new LongRunningAction("thread-3", phaser));
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phaser in must be in phase 1. actual phase = "+phaser.getPhase());//this position phase is must be 1.
        //Now will create 2 more threads which will be waiting on the barrier until we will be call the arriveAndAwaitAdvance()
        // We can leverage Phaser to achieve that because it allows us to configure dynamically the number of threads
        // that should wait on the barrier.
        executorService.submit(new LongRunningAction("thread-4",phaser));
        executorService.submit(new LongRunningAction("thread-5",phaser));
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phaser in must be in phase 2. actual phase = "+phaser.getPhase());//this position phase is must be 2.
        phaser.arriveAndDeregister();
    }
}
