package org.example.thread.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Problem: is to find a sum of all numbers from 1 to 100.
 * We can do it by looping 1 to 100 sequentially and adding them.
 *
 * Another way we can do it by the divide and Conquer rule.
 * Group the numbers in a way so each group has exactly two elements. Then Assign that group to a pool of threads
 * So each thread returns a partial sum in parallel.
 * Then collect those partial sums and add them to get the whole sum.A manager class, which is responsible for grouping integers,
 * and submit the group to the Executor Framework for partial add.
 * Collect the partial sum, wait until all partial sums return, and add them.
 */
public class ParallelAdder {

    public Integer parallelSum(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<Integer>> futures = new ArrayList<>();
        int count =1;
        int prv = 0;
        for (int i=1;i<=100;i++){
            if(count%2==0){
//                System.out.println("Prev : "+prv+" current "+i);
                Future<Integer> future = executorService.submit(new CallableAdder(prv, i));
                futures.add(future);
                count=1;
                continue;
            }
            prv=i;
            count++;
        }
        int totalSum = 0;
        for(Future<Integer> f: futures){
            try {
                totalSum = totalSum + f.get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total Sum = "+totalSum);
        executorService.shutdown();
        return totalSum;
    }

    public int sequentialSum(){
        Integer totalSum = 0;
        for (int i=1;i<=100;i++){
            totalSum = totalSum + i;
        }
        System.out.println("Sequential total sum = "+totalSum);
        return totalSum;
    }

    /**
     * Sequential sum is faster in this example but this example is to show @{@link Future} behaviour.
     * @param args
     */
    public static void main(String[] args) {
        ParallelAdder parallelAdder = new ParallelAdder();
        long startTime = System.currentTimeMillis();
        Integer parallelSum = parallelAdder.parallelSum();
        System.out.println("Parallel sum time taken "+(System.currentTimeMillis()-startTime));
        long seqStartTime = System.currentTimeMillis();
        int sequentialSum = parallelAdder.sequentialSum();
        System.out.println("Sequential sum time taken "+(System.currentTimeMillis()-seqStartTime));
    }
}