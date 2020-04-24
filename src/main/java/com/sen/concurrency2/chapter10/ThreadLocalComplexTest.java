package com.sen.concurrency2.chapter10;

/**
 * @Author: Sen
 * @Date: 2019/12/10 17:59
 * @Description: {@link ThreadLocal} 测试线程不可见行
 * {@link ThreadLocal} 数据结构是k，v形式
 * k-线程id
 * v-每个线程锁设置的值
 */
public class ThreadLocalComplexTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            threadLocal.set("T1");
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        });

        Thread t2 = new Thread(() -> {
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
        // main线程阻塞等t1,t2
        t1.join();
        t2.join();
        System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
    }
}
