package org.example.thread;
import java.util.concurrent.CountDownLatch;
/**
 * We need to test that class will only create a single instance of MyObject even when multiple threads access it parallely.
 * To test this we will create a CountDownLatch and initialize it with 1.
 * Then each thread will wait in its run method until the count down of the latch reaches zero.
 * Because CountDownLatch is initialized with 1, a single thread call to countDown method will trigger
 * all other threads to start at approximately the same time.
 */
public class CountDownLatchTest {
    private static class MyObject{
        public MyObject(){
            System.out.println("Object is initialized!");
        }
    }
    private static class ObjectFactory{
        private static volatile MyObject instance;
        public static MyObject getInstance(){
            if(instance == null){
                synchronized (ObjectFactory.class){
                    if (instance == null){
                        instance = new MyObject();
                    }
                }
            }
            return instance;
        }
    }
    private static class MyThread extends Thread{
        private CountDownLatch latch = null;
        private MyObject instance;
        public MyThread(CountDownLatch latch){
            super();
            this.latch = latch;
        }
        @Override
        public void run(){
            try {
                latch.await();
                instance = ObjectFactory.getInstance();
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }
    public void shouldCreateOnlySingleInstanceOfMyObjectClassWhenTestedWithParallelThreads() throws Exception{
        final ObjectFactory factory = new ObjectFactory();
        final CountDownLatch startSignal = new CountDownLatch(1);
        int threadCount = 1000;
        MyThread[] threads = new MyThread[threadCount];
        for (int i=0;i<threadCount;i++){
            threads[i]=new MyThread(startSignal);
            threads[i].start();
        }
        startSignal.countDown();
        for (MyThread thread : threads){
            thread.join();
        }
        MyObject instance = factory.getInstance();
        for (MyThread thread : threads){
            if(thread.instance != instance){
                System.out.println("Count down test failed, created more than one instances!");
                break;
            }
        }
    }
    public static void main(String[] args) {
        CountDownLatchTest test = new CountDownLatchTest();
        try {
            test.shouldCreateOnlySingleInstanceOfMyObjectClassWhenTestedWithParallelThreads();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
