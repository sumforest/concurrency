package com.sen.concurrency3.juc.utils.phaser;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @Auther: Sen
 * @Date: 2019/12/16 22:24
 * @Description: awaitAdvance()阶段与当前Phaser相同阻塞，否则立即返回
 */
public class PhaserExample6 {

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);

       /* System.out.println(phaser.getPhase());
        System.out.println(phaser.getUnarrivedParties());
        System.out.println(phaser.getArrivedParties());
        new Thread(() -> phaser.awaitAdvance(phaser.getPhase()));
        System.out.println(phaser.getPhase());
        System.out.println(phaser.getUnarrivedParties());
        System.out.println(phaser.getArrivedParties());
        System.out.println("+++++++++");*/

        IntStream.rangeClosed(1, 5).boxed().map(i -> phaser).forEach(AwaitAdvanceTask::new);
        System.out.println(phaser.getPhase());
        phaser.awaitAdvance(phaser.getPhase());
        System.out.println(phaser.getPhase());
        System.out.println("+++++++++++++++++++++");
    }

    private static class AwaitAdvanceTask extends Thread{

        private static final Random random = new Random(System.currentTimeMillis());

        private final Phaser phaser;

        private AwaitAdvanceTask(Phaser phaser) {
            this.phaser = phaser;
            start();
        }

        @Override
        public void run() {
            System.out.println(getName() + " start");
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                phaser.arriveAndAwaitAdvance();
                System.out.println(getName() + " end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
