package com.sen.concurrency3.juc.utils.phaser;

import java.sql.SQLOutput;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/16 18:43
 * @Description: 计数器可重复使用，{@link Phaser#arriveAndAwaitAdvance()}来模拟铁人三项比赛，当所有的运动员
 * 都完成了一个项目后才开始另外一个项目；经行另一个项目后计数器的值被重置了
 */
public class PhaserExample2 {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        for (int i = 0; i < 5; i++) {
            new Athlete(i, phaser).start();
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
                System.out.println(no + Thread.currentThread().getName() + " start running");
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
                System.out.println(no + Thread.currentThread().getName() + " end running");
                phaser.arriveAndAwaitAdvance();

                System.out.println(no + Thread.currentThread().getName() + " start bicycle");
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
                System.out.println(no + Thread.currentThread().getName() + " end bicycle");
                phaser.arriveAndAwaitAdvance();

                System.out.println(no + Thread.currentThread().getName() + " start swim");
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
                System.out.println(no + Thread.currentThread().getName() + " end swim");
                phaser.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
