package com.sen.concurrency3.juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/16 18:43
 * @Description: 计数器可重复使用
 */
public class PhaserExample3 {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        for (int i = 1; i < 5; i++) {
            new Athlete(i, phaser).start();
        }
        new InjuredAthlete(5, phaser).start();
    }
    private static class InjuredAthlete extends Thread {

        private final int no;

        private final Phaser phaser;

        private InjuredAthlete(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                Sport(no, phaser, " start running", " end running");

                Sport(no, phaser, " start bicycle", " end bicycle");

                System.out.println("On shit! I am injured.I will exit");
                phaser.arriveAndDeregister();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
     private static class Athlete extends Thread{

        private final int no;

        private final Phaser phaser;

        private Athlete(int no, Phaser phaser) {
            this.no = no;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                Sport(no, phaser, " start running", " end running");

                Sport(no, phaser, " start bicycle", " end bicycle");

                Sport(no, phaser, " start swim", " end swim");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
     }
    private static void Sport(int no, Phaser phaser, String s, String s2) throws InterruptedException {
        System.out.println(no + Thread.currentThread().getName() + s);
        TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
        System.out.println(no + Thread.currentThread().getName() + s2);
        phaser.arriveAndAwaitAdvance();
    }
}
