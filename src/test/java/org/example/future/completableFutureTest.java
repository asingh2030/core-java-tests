package org.example.future;

import org.example.thread.future.CompletableFutureExample;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class completableFutureTest {
    @Test
    public void calculateAsync() {
        CompletableFutureExample test = new CompletableFutureExample();
        try {
            Future<String> future = test.calculateAsync();
            String result = future.get();
            System.out.println("calculateAsync - "+result);
            Assert.assertEquals("Hello",result);
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void supplyAsyncTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        Future<String> future = test.supplyAsync();
        try {
            Assert.assertEquals("Hello", future.get());
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void thenApplyTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        Future<String> future = test.thenApply();
        try {
            Assert.assertEquals("Hello world!", future.get());
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void thanAcceptTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        CompletableFuture<Void> completableFuture = test.thenAccept();
        try{
            Assert.assertTrue(completableFuture.get() == null);
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void thenRunTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        CompletableFuture<Void> completableFuture = test.thenRun();
        try {
            Assert.assertTrue(completableFuture.get() == null);
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void thenComposeTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        CompletableFuture<String> completableFuture = test.thenCompose();
        try {
            String result = completableFuture.get();
            Assert.assertEquals("Hello world!", result);
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail(e.getMessage());
        }
    }
    @Test
    public void thenCombineTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        CompletableFuture<String> completableFuture = test.thenCombine();
        try {
            String result = completableFuture.get();
            Assert.assertEquals("Hello world!", result);
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail(e.getMessage());
        }
    }
    @Test
    public void thenAcceptBothTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        CompletableFuture<Void> completableFuture = test.thenAcceptBoth();
        try {
            Void result = completableFuture.get();
            Assert.assertTrue(result == null);
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail(e.getMessage());
        }
    }
    @Test
    public void thenAllOfTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        CompletableFuture<Void> completableFuture = test.thenAllOf();
        try {
            Void result = completableFuture.get();
            Assert.assertTrue(result == null);
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void thenAllJoinTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        Assert.assertEquals("Hello beautiful world!", test.thenJoin());
    }

    @Test
    public void handleExceptionTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        CompletableFuture<String> future = test.handleException(null);
        try {
            String result = future.get();
            Assert.assertEquals("Hello Stranger!", result);
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail();
        }
    }

    @Test
    public void completeExceptionallyTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        CompletableFuture<String> future = test.completeExceptionally();
        try {
            String s = future.get();
            Assert.fail("Expecting runtime exception!");
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ExecutionException);
        }
    }

    @Test
    public void asyncExecutionTest(){
        CompletableFutureExample test = new CompletableFutureExample();
        CompletableFuture<String> future = test.asyncExecution();
        try {
            String result = future.get();
            Assert.assertEquals("Hello world!", result);
        } catch (InterruptedException | ExecutionException e) {
            Assert.fail(e.getMessage());
        }

    }

}
