package com.sen.concurrency3.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/16 01:42
 * @Description: {@link Semaphore} 代表临界资源，调用 {@code acquire(2)} 表示获取两份临界资源，当临界资源
 * 不足时，请求临界资源的线程将会被阻塞而进入阻塞队列
 */
public class SemaphoreExample2 {

    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + " in");
                try {
                    semaphore.acquire(2);
                    System.out.println(Thread.currentThread().getName() + " get the semaphore");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release(2);
                    System.out.println(Thread.currentThread().getName() + " out");
                }
            }).start();
        }
        while (true) {
            System.out.println("AP->" + semaphore.availablePermits());
            // 返回系统评估的因请求资源而被阻塞的线程数
            System.out.println("QL->" + semaphore.getQueueLength());
            TimeUnit.SECONDS.sleep(3);
        }
    }
}
