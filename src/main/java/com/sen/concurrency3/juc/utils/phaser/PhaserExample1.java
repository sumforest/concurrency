package com.sen.concurrency3.juc.utils.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/16 18:28
 * @Description: 动态增加参与者parties
 */
public class PhaserExample1 {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser();
        IntStream.rangeClosed(1, 5).boxed().map(i -> phaser).forEach(PhaserTask::new);
        phaser.register();
        // 所有参与者都到达后经行下一个阶段
        phaser.arriveAndAwaitAdvance();
        System.out.println("All worker is finished");
    }

    private static class PhaserTask extends Thread {

        private final Phaser phaser;

        private PhaserTask(Phaser phaser) {
            this.phaser = phaser;
            // 注册
            phaser.register();
            // 启动
            start();
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
                System.out.println(Thread.currentThread().getName() + " is working");
                phaser.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
