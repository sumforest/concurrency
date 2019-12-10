package com.sen.concurrency2.chapter10;

/**
 * @Auther: Sen
 * @Date: 2019/12/10 17:59
 * @Description:
 */
public class ThreadLocalSimulatorTest {

    private static ThreadLocalSimulator<String> threadLocal = new ThreadLocalSimulator<>();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            threadLocal.set("T1");
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        });

        Thread t2 = new Thread(()->{
            threadLocal.set("T2");
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
    }
}
