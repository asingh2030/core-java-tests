package org.example.thread.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureExample {
    /**
     * CompletableFuture class implements the Future interface,
     * so we can use it as a Future implementation, but with additional completion logic.
     * @return @{@link Future}
     * @throws InterruptedException
     */
    public Future<String> calculateAsync() throws InterruptedException{
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Executors.newFixedThreadPool(1).submit(()->{
            Thread.sleep(500);
            CompletableFuture<String> completableFuture1 = CompletableFuture.completedFuture("Hello");
            return null;
        });
        return completableFuture;
    }

    public Future<String> supplyAsync(){
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->"Hello");
        return completableFuture;
    }

    /**
     * thenApply() method accepts a Function instance, uses it to process the result,
     * and returns a Future that holds a value returned by a function.
     * @return @{@link Future}
     */
    public Future<String> thenApply(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->"Hello");
        return future.thenApply(s->s+" world!");
    }

    /**
     * The thenAccept method receives a Consumer and passes it the result of the computation.
     * Then the final future.get() call returns an instance of the Void type.
     * @return CompletableFuture
     */
    public CompletableFuture<Void> thenAccept(){
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        return completableFuture.thenAccept(s -> System.out.println("Computation returned:"+s));
    }

    /**
     * if we neither need the value of the computation,
     * nor want to return some value at the end of the chain,
     * then we can pass a Runnable lambda to the thenRun method.
     * @return CompletableFuture
     */
    public CompletableFuture<Void> thenRun(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()-> "Hello");
        return future.thenRun(()->System.out.println("Computation completed!"));
    }

    /**
     * CompletableFuture API is the ability to combine CompletableFuture instances in a chain of computation steps.
     * The result of this chaining is itself a CompletableFuture that allows further chaining and combining.
     * This approach is ubiquitous in functional languages and is often referred to as a monadic design pattern.
     * In the following example we use the thenCompose method to chain two Futures sequentially.
     * @return CompletableFuture
     */
    public CompletableFuture<String> thenCompose(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->"Hello")
                .thenCompose(s->CompletableFuture.supplyAsync(()->s+" world!"));
        return future;
    }

    /**
     * If we want to execute two independent Futures and do something with their results,
     * we can use the thenCombine method that accepts a Future and a Function with two arguments to process both results.
     * @return CompletableFuture
     */
    public CompletableFuture<String> thenCombine(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->"Hello")
                .thenCombine(CompletableFuture.supplyAsync(()-> " world!"), (s1, s2)->s1+s2);
        return future;
    }

    /**
     * we want to do something with two Futures‘ results,
     * but don't need to pass any resulting value down a Future chain.
     * The thenAcceptBoth method will do the same.
     * @return CompletableFuture
     */
    public CompletableFuture<Void> thenAcceptBoth(){
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(()->"Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(()->" world!"),(s1,s2)->System.out.println("Execution completed result "+s1+s2));
        return completableFuture;
    }

    /**
     * When we need to execute multiple Futures in parallel,
     * we usually want to wait for all of them to execute and then process their combined results.
     *
     * The CompletableFuture.allOf static method allows to wait for completion of all of the Futures provided as a var-arg.
     * The return type of the CompletableFuture.allOf() is a CompletableFuture<Void>.
     * The limitation of this method is that it does not return the combined results of all Futures.
     * @return CompletableFuture
     */
    public CompletableFuture<Void> thenAllOf(){
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> "beautiful");
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> "world!");
        CompletableFuture<Void> future = CompletableFuture.allOf(completableFuture, completableFuture1, completableFuture2);
        return future;
    }

    /**
     * Execute {@link CompletableFuture} parallel/ async and at last combine the results of all given future.
     * The CompletableFuture.join() method is similar to the get method,
     * but it throws an unchecked exception in case the Future does not complete normally.
     * This makes it possible to use it as a method reference in the Stream.map() method.
     * @return String
     */
    public String thenJoin(){
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> "beautiful");
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> "world!");

        return Stream.of(completableFuture, completableFuture1, completableFuture2)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
    }

    /**
     * Instead of catching an exception in a syntactic block,
     * the CompletableFuture class allows us to handle it in a special handle method.
     * This method receives two parameters: a result of a computation (if it finished successfully),
     * and the exception thrown (if some computation step did not complete normally).
     *
     * In the following example, we use the handle method to provide a default value when the asynchronous computation
     * of a greeting was finished with an error because no name was provided.
     * @return String
     */
    public CompletableFuture<String> handleException(String name){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            if(name == null){
                throw new RuntimeException("Name must not be null!");
            }
            return "Hello "+name;
        }).handle((s, t)->s != null ? s : "Hello Stranger!");
        return future;
    }

    /**
     * {@link CompletableFuture} has the ability to complete it with an exception.
     * The completeExceptionally method is used for same.
     * The completableFuture.get() method in the following example throws an
     * ExecutionException with a RuntimeException as its cause.
     * @return CompletableFuture
     */
    public CompletableFuture<String> completeExceptionally(){
        CompletableFuture future = CompletableFuture.supplyAsync(()->"Hello world!");
        future.completeExceptionally(new RuntimeException("throws exceptionally!"));
        return future;
    }

    /**
     * CompletableFuture class have two additional variants with the Async postfix.
     * These methods are usually intended for running a corresponding step of execution in another thread.
     * The methods without the Async postfix run the next execution stage using a calling thread.
     * The Async method without the Executor argument runs a step using the common fork/join pool implementation
     * of Executor that is accessed with the ForkJoinPool.commonPool() method.
     * Finally, the Async method with an Executor argument runs a step using the passed Executor.
     * @return CompletableFuture
     */
    public CompletableFuture<String> asyncExecution(){
        return CompletableFuture.supplyAsync(()->"Hello")
        .thenApplyAsync(s->s+" world!");
    }
}
