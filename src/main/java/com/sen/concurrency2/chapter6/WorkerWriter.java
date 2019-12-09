package com.sen.concurrency2.chapter6;

import java.util.Random;

/**
 * @Auther: Sen
 * @Date: 2019/12/9 20:21
 * @Description:
 */
public class WorkerWriter extends Thread {

    private Random random = new Random(System.currentTimeMillis());

    private SharedData data;

    private String filter;

    private int index = 0;

    public WorkerWriter(SharedData data, String filter) {
        this.data = data;
        this.filter = filter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                data.wirteData(nextChar());
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private char nextChar() {
        if (index > filter.length() - 1)
            index = 0;
        char next = filter.charAt(index);
        index++;
        return next;
    }
}
