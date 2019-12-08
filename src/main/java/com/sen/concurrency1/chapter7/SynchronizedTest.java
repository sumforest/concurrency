package com.sen.concurrency1.chapter7;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 17:09
 * @Description: 通过jconsole和jstack命令以及javap -c查看synchronized关键字
 */
public class SynchronizedTest {
    private final static Object MONITOR = new Object();
    public static void main(String[] args) {
        Runnable runnable = ()->{
            synchronized (MONITOR) {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(200_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();
    }
}
