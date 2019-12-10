package com.sen.concurrency2.chapter16;

import java.util.Random;

/**
 * @Auther: Sen
 * @Date: 2019/12/10 23:53
 * @Description:
 */
public class CounterIncrement extends Thread {

    private int counter = 0;

    private volatile boolean start = true;

    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public void run() {
        try {
            while (start) {
                System.out.println(Thread.currentThread().getName() + " counter: " + counter++);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            // e.printStackTrace();
        } finally {
            clean();
        }
    }

    private void clean() {
        System.out.println("before shutdown thread clean resources, counter = " + counter);
    }

    public void shutdown() {
        this.start = false;
        this.interrupt();
    }
}
