package org.example.thread.producer_consumer.canteen;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class StudentConsumer implements Runnable {
    private String name;
    private CompletionService<FoodPlate> service;

    public StudentConsumer(String name, CompletionService<FoodPlate> service) {
        this.name = name;
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompletionService<FoodPlate> getService() {
        return service;
    }

    public void setService(CompletionService<FoodPlate> service) {
        this.service = service;
    }

    @Override
    public void run() {
        System.out.println("Student waiting for food plate - "+this.getName()+" at "+ LocalDateTime.now());
        try {
            Future<FoodPlate> foodPlateFuture = service.take();
            FoodPlate plate = foodPlateFuture.get();
            System.out.println("Student got the food plate "+this.getName()+" created by "+plate.getCreatedBy()+" that consumed at "+LocalDateTime.now());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
