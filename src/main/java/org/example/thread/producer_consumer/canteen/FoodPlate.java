package org.example.thread.producer_consumer.canteen;

/**
 * POJO class represent Food plate in canteen service.
 */
public class FoodPlate {
    private boolean isCurryReady;
    private boolean isGrainsReady;
    private boolean isChapatiReady;
    private boolean isOtherJunkReady;
    private String createdBy;


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isCurryReady() {
        return isCurryReady;
    }

    public void setCurryReady(boolean curryReady) {
        isCurryReady = curryReady;
    }

    public boolean isGrainsReady() {
        return isGrainsReady;
    }

    public void setGrainsReady(boolean grainsReady) {
        isGrainsReady = grainsReady;
    }

    public boolean isChapatiReady() {
        return isChapatiReady;
    }

    public void setChapatiReady(boolean chapatiReady) {
        isChapatiReady = chapatiReady;
    }

    public boolean isOtherJunkReady() {
        return isOtherJunkReady;
    }

    public void setOtherJunkReady(boolean otherJunkReady) {
        isOtherJunkReady = otherJunkReady;
    }
}
