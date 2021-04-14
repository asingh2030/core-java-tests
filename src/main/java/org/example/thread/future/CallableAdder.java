package org.example.thread.future;

import java.util.concurrent.Callable;

/**
 * Add given two operands and returns the Sum.
 */
public class CallableAdder implements Callable<Integer> {

    private Integer operand1;
    private Integer operand2;

    public CallableAdder(Integer operand1, Integer operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    @Override
    public Integer call() throws Exception {
        Integer partialSum = operand1+operand2;
//        System.out.println(Thread.currentThread().getName()+" : partial sum for operand1 "+operand1+" + operand2 "+operand2+" = "+partialSum);
        return partialSum;
    }
}
