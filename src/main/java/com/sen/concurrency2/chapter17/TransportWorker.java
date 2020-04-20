package com.sen.concurrency2.chapter17;

import java.util.Random;

/**
 * @Author: Sen
 * @Date: 2019/12/11 02:11
 * @Description:
 */
public class TransportWorker extends Thread {
    private final Channel channel;

    private final static Random RANDOM = new Random(System.currentTimeMillis());


    public TransportWorker(Channel channel,String name) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        for (int i = 0; true ; i++) {
            String desc = "Request--" + i;
            System.out.println(getName() + " put " + desc + " on channel");
            channel.put(new Request(desc));
            try {
                Thread.sleep(RANDOM.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
