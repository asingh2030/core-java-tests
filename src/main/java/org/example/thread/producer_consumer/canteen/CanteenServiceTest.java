package org.example.thread.producer_consumer.canteen;

import java.util.concurrent.CompletionService;

/**
 * Canteen service test class to test all three scenarios
 * Scenario1: Canteen Staff (Producers) preparing food plates
 *            and no students yet at counter
 * Scenario2: Students (Consumers) at the canteen counter
 *            but no food plates yet available.
 *            Remember to comment out the two submit calls from above
 *            to simulate this situation. Note that the following
 *            thread would block since we have used CompletionService.take
 *            If you need an unblocking retrieval of completed tasks
 *            (retrieval of food plates), use poll method.
 * Scenario3: For random Producers and Consumers, please uncomment submit() method calls.
 */

public class CanteenServiceTest {

    public static void main(String[] args) {
        /*
         * Scenario1: Canteen Staff (Producers) preparing food plates
         * and no students yet at counter
         */
        //Create few canteen staff's/ producers
        CanteenStaffProducer staffProducer1 = new CanteenStaffProducer("Staff1");
        CanteenStaffProducer staffProducer2 = new CanteenStaffProducer("Staff2");

        CompletionService<FoodPlate> completionService = CompletionServiceProvider.getCompletionService();
        completionService.submit(staffProducer1);
        completionService.submit(staffProducer2);

        //Scenario2: Students (Consumers) at the canteen counter
        //but no food plates yet available.
        //Remember to comment out the two submit calls from above
        //to simulate this situation. Note that the following
        //thread would block since we have used CompletionService.take
        //If you need an unblocking retrieval of completed tasks
        //(retrieval of food plates), use poll method.
        StudentConsumer consumer1 = new StudentConsumer("Student1",completionService);
        StudentConsumer consumer2 = new StudentConsumer("Student2",completionService);
        new Thread(consumer1).start();
        new Thread(consumer2).start();
        //Scenario3: For random Producers and Consumers, please uncomment submit() method calls.
        CompletionServiceProvider.shutdown();
    }
}
