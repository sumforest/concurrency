package com.sen.concurrency2.chapter17;

import java.util.Random;

/**
 * @Author: Sen
 * @Date: 2019/12/11 01:34
 * @Description:
 */
public class WorkerThread extends Thread {

    private final Channel channel;

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            Request request = channel.take();
            System.out.println(getName() + " take " + request);
            try {
                Thread.sleep(RANDOM.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
