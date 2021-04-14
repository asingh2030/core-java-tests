package org.example.thread.producer_consumer.canteen;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * Class that represent canteen staff in canteen service.
 * These staff will prepare food plate.
 * These staff treated as Producer task.
 */
public class CanteenStaffProducer implements Callable<FoodPlate> {
    private String staffName;

    public CanteenStaffProducer(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffName() {
        return staffName;
    }

    @Override
    public FoodPlate call() throws Exception {
        System.out.println("Current canteen staff at work: "+this.staffName+" at "+ LocalDateTime.now());
        //Simulate time required to prepare food paltes using Thread.sleep()
        Thread.sleep(2000L);

        FoodPlate plate = new FoodPlate();
        plate.setChapatiReady(true);
        plate.setCurryReady(true);
        plate.setGrainsReady(true);
        plate.setOtherJunkReady(true);
        plate.setCreatedBy(this.getStaffName());
        return plate;
    }
}
