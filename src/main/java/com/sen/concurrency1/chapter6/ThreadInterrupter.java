package com.sen.concurrency1.chapter6;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 03:16
 * @Description:
 */
public class ThreadInterrupter {

    private static Object monitor = new Object();

    public static void main(String[] args) throws InterruptedException {

        /*Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(100);
                        System.out.println(">>" + this.isInterrupted());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        System.out.println(t1.isInterrupted());
        Thread.sleep(200);
        t1.interrupt();
        System.out.println(t1.isInterrupted());*/

        /*Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    while (true) {
                        synchronized (monitor){
                            try {
                                monitor.wait(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        System.out.println(t1.isInterrupted());
        Thread.sleep(200);
        t1.interrupt();
        System.out.println(t1.isInterrupted());*/

        Thread t1 = new Thread(() -> {
            try {
                while (true) {
                    // System.out.println(">>" + this.isInterrupted());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread main = Thread.currentThread();
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            main.interrupt();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(main.isInterrupted());
        });

        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main:" + main.isInterrupted());
        System.out.println("主线程结束");
    }
}
