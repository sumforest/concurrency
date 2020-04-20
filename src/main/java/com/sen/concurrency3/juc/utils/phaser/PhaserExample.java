package com.sen.concurrency3.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/16 23:05
 * @Description: 强制终止
 */
class PhaserExample8 {

    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(2);
        new Thread(phaser::arriveAndAwaitAdvance).start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println(phaser.isTerminated());
        phaser.forceTermination();
        System.out.println(phaser.isTerminated());
    }
}
