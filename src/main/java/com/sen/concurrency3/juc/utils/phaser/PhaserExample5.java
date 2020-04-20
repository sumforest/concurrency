package com.sen.concurrency3.juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/16 22:13
 * @Description: arrive()立即返回
 */
public class PhaserExample5 {

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(6);
        for (int i = 0; i < 5; i++) {
            new ArriveTask(phaser, "PhaserTest:" + i);
        }
        phaser.arriveAndAwaitAdvance();
        System.out.println("All parties have been arrived");
    }

    private static class ArriveTask extends Thread{

        private final Random random = new Random(System.currentTimeMillis());

        private final Phaser phaser;

        public ArriveTask(Phaser phaser,String name) {
            this.phaser = phaser;
            setName(name);
            start();
        }

        @Override
        public void run() {
            System.out.println(getName() + " start");
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + " finished");
            phaser.arrive();
        }
    }
}
