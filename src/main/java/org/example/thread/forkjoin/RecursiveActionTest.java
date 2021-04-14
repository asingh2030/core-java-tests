package org.example.thread.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
/**
 * @author Ashutosh Singh
 * Test class for -
 * Multiply the given large arrays element with given number.
 * Using ForkJoin RecursiveAction abstract class will executes in parallel.
 */
public class RecursiveActionTest {
    static final int SIZE = 1_00_000;
    static int[] array = randomArray();
    public static void main(String[] args) {
        System.out.println("First 10 element of array before transform - ");
        print();
        int number = 3;
        //Invoking ForkJoin Action using ForkJoin Constructor pool
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ArrayTransform(0,SIZE,number,array));
        System.out.println("\nFirst 10 element of the array after transform - ");
        print();

        //Invoking ForkJoin Action using default pool
        System.out.println("\nFirst 10 element of the array before transform - ");
        print();
        ArrayTransform transform = new ArrayTransform(0,SIZE,2,array);
        transform.invoke();
        System.out.println("\nFirst 10 element of the array after transform - ");
        print();


    }
    static void print(){
        for(int i=0;i<10;i++){
            System.out.print(array[i]+", ");
        }
    }
    static int[] randomArray() {
        int[] array = new int[SIZE];
        Random random = new Random();

        for (int i = 0; i < SIZE; i++) {
            array[i] = random.nextInt(100);
        }

        return array;
    }

    /**
     * Multiply the given large arrays element with given number.
     * Using ForkJoin RecursiveAction abstract class will executes in parallel.
     */
    static class ArrayTransform extends RecursiveAction{
        int start;
        int end;
        int number;
        int[] array;
        static final int threshold = 1_00_000;

        public ArrayTransform(int start, int end, int number, int[] array) {
            this.start = start;
            this.end = end;
            this.number = number;
            this.array = array;
        }

        @Override
        protected void compute() {
            if(end-start < threshold){
                computeDirectly();
            }else{
                int middle = (end + start) / 2;
                ArrayTransform t1 = new ArrayTransform(start, middle, number,array);
                ArrayTransform t2 = new ArrayTransform(middle, end, number,array);
                invokeAll(t1,t2);
            }
        }

        private void computeDirectly() {
            for (int i = start; i < end ; i++){
                array[i] = array[i] * number;
            }
        }
    }
}
