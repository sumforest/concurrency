package com.sen.concurrency1.chapter10;

import java.util.stream.Stream;

/**
 * @Author: Sen
 * @Date: 2019/12/8 01:32
 * @Description: 自定义一个显示锁，不可重入
 */
public class BooleanLockTest {

    public static void main(String[] args) throws InterruptedException {
        BooleanLock booleanLock = new BooleanLock();

        Stream.of("T1","T2","T3","T4").forEach(name-> new Thread(()->{
            try {
                booleanLock.lock(100);
                System.out.println(Thread.currentThread().getName() + " is working");
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Lock.WaitLockTimeOutException e) {
                e.printStackTrace();
            } finally {
                booleanLock.unlock();
            }
        },name).start());

        Thread.sleep(1_000);
        booleanLock.unlock();
    }
}
