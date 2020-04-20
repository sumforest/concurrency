package com.sen.concurrency3.juc.utils.phaser;

import java.sql.Time;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Sen
 * @Date: 2019/12/16 22:48
 * @Description: 打断
 */
public class PhaserExample7 {

    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(2);
        /*Thread t1 =  new Thread(phaser::arriveAndAwaitAdvance);
        t1.start();
        TimeUnit.SECONDS.sleep(3);
        t1.interrupt();
        System.out.println("======== Finished =======");*/

        /*Thread t1 =  new Thread(()-> {
            try {
                phaser.awaitAdvanceInterruptibly(phaser.getPhase());
                System.out.println(Thread.currentThread().getName() + " out");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        TimeUnit.SECONDS.sleep(3);
        t1.interrupt();
        System.out.println("======== Finished =======");*/

        Thread t1 =  new Thread(()-> {
            try {
                phaser.awaitAdvanceInterruptibly(phaser.getPhase(),2,TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + " out");
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        TimeUnit.SECONDS.sleep(3);
        t1.interrupt();
        System.out.println("======== Finished =======");
    }
}
