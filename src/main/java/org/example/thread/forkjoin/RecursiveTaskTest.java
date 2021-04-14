package org.example.thread.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @author Ashutosh Singh
 * Count the occurance of even numbers in large array.
 * Using RecursiveTask concurrent class that uses internally Fork/ join framework.
 * Parallel processing with concurrence to reduce processing time.
 */
public class RecursiveTaskTest {
    static final int SIZE = 1_00_00_000;

    public static void main(String[] args) {
        ArrayCounter counter = new ArrayCounter(0,SIZE,randomArray());
        ForkJoinPool pool = new ForkJoinPool();
        Integer eventNumbersCount = pool.invoke(counter);
        System.out.println("Total count of Even numbers = "+eventNumbersCount);
    }

    private static int[] randomArray() {
    Random random = new Random();
    int[] arr = new int[SIZE];
    IntStream.range(0,SIZE).forEach(n->arr[n]=random.nextInt(100));
    return arr;
    }


    static class ArrayCounter extends RecursiveTask<Integer>{
        int start = 0;
        int end ;
        int[] arr;

        public ArrayCounter(int start, int end, int[] arr) {
            this.start = start;
            this.end = end;
            this.arr = arr;
        }

        @Override
        protected Integer compute() {
            int count = 0;
            if((end - start) < SIZE){
                return computeDirectly();
            }else{
                int middle = (end+start)/2;
                ArrayCounter subTask1 = new ArrayCounter(start,middle,arr);
                ArrayCounter subTask2 = new ArrayCounter(middle,end,arr);
                //Using Internal or default ForkJoinPool
                invokeAll(subTask1, subTask2);
                return subTask1.join()+ subTask2.join();
            }
        }

        private Integer computeDirectly() {
            Integer count = 0;
            for (int i=start; i<end; i++){
                if(arr[i]%2 == 0){
                    count++;
                }
            }
            return count;
        }
    }
}
