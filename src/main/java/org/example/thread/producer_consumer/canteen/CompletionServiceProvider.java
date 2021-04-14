package org.example.thread.producer_consumer.canteen;

import java.util.concurrent.*;

/**
 * Helper class to initialize CompletionService.
 */
public class CompletionServiceProvider {
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);
    private static final CompletionService<FoodPlate> completionService = new ExecutorCompletionService<>(executor);

    public static Executor getExecutor() {
        return executor;
    }

    public static CompletionService<FoodPlate> getCompletionService() {
        return completionService;
    }

    public static void shutdown(){
        executor.shutdown();
    }
}
