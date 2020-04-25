package com.sen.concurrency3.juc.utils.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/16 19:23
 * @Description: {@link Phaser} 一些相关属性
 */
public class PhaserExample4 {

    public static void main(String[] args) {
        // final Phaser phaser = new Phaser(1);

        /*System.out.println(phaser.getPhase());
        phaser.arriveAndAwaitAdvance();
        System.out.println(phaser.getPhase());

        phaser.arriveAndAwaitAdvance();
        System.out.println(phaser.getPhase());

        phaser.arriveAndAwaitAdvance();
        System.out.println(phaser.getPhase());*/

        /*System.out.println(phaser.getRegisteredParties());
        phaser.register();
        System.out.println(phaser.getRegisteredParties());
        phaser.register();
        System.out.println(phaser.getRegisteredParties());
        phaser.register();
        System.out.println(phaser.getRegisteredParties());

        System.out.println(phaser.getArrivedParties());
        System.out.println(phaser.getUnarrivedParties());*/

        /*System.out.println(phaser.getArrivedParties());
        System.out.println(phaser.getUnarrivedParties());
        phaser.bulkRegister(10);
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());
        System.out.println(phaser.getUnarrivedParties());*/

       final Phaser phaser = new Phaser(2){
           @Override
           protected boolean onAdvance(int phase, int registeredParties) {
               return false;
           }
       };

       new OnAdvanceTask("Alex", phaser).start();
       new OnAdvanceTask("HaSee", phaser).start();

    }

    private static class OnAdvanceTask extends Thread{

        private final String name;

        private final Phaser phaser;

        private OnAdvanceTask(String name, Phaser phaser) {
            this.name = name;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(name + " I am start at the phaser " + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
            System.out.println("isTerminated: "+phaser.isTerminated());
            System.out.println(name + " I am end at the phaser " + phaser.getPhase());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if ("Alex".equals(name)) {
                System.out.println(name + " I am start at the phaser " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();
                System.out.println(name + " I am end at the phaser " + phaser.getPhase());
            }
        }
    }
}
