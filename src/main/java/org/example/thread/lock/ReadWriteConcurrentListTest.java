package org.example.thread.lock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * @author Ashutosh Singh
 *
 */
class ReadWriteReentrantLockTest{

    public static void main(String[] args) {
        Integer[] initEle = {23, 23, 12, 24};
        ReadWriteConcurrentList<Integer> list = new ReadWriteConcurrentList<Integer>(initEle);

        IntStream.range(0,2).forEach(n->{
            Thread writer = new Thread(()->{
                Random random = new Random();
                Integer number = random.nextInt(100);
                list.add(number);
                try {
                    Thread.sleep(1000);
                    System.out.println("Writer-"+n+" : "+number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            writer.start();
        });

        IntStream.range(0,9).forEach(n-> {
            Thread reader = new Thread(() -> {
                Random random = new Random();
                int index = random.nextInt(list.size());
                Integer number = list.get(index);
                System.out.println("Reader-"+n+" : " + number);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            reader.start();
        });
    }
    static class ReadWriteConcurrentList<E> {
        private List<E> list = new ArrayList<>();
        private ReadWriteLock lock = new ReentrantReadWriteLock();

        ReadWriteConcurrentList(E... initialEl){
            list.addAll(Arrays.asList(initialEl));
        }

        public void add(E el){
            Lock wL = this.lock.writeLock();
            wL.lock();
            try{
                list.add(el);
            }finally {
                wL.unlock();
            }
        }

        public E get(int index){
            Lock rLock = lock.readLock();
            rLock.lock();
            E element = null;
            try{
                element =   list.get(index);
            }finally {
                rLock.unlock();
            }
            return element;
        }

        public int size(){
            Lock rLock = lock.readLock();
            rLock.lock();
            int size = 0;
            try{
                size =   list.size();
            }finally {
                rLock.unlock();
            }
            return size;
        }
    }
}
