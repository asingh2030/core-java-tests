package org.example.thread;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class TestTimerTask extends TimerTask {
    @Override
    public void run() {
        System.out.println("Timer task started - "+ LocalDateTime.now());
        completeTask();
        System.out.println("Timer task Ended - "+ LocalDateTime.now());
    }

    private void completeTask() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TimerTask task = new TestTimerTask();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(task, 0, 10*1000);
        System.out.println("TimerTask started");
        //cancel after sometime
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask cancelled");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
