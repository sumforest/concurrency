package com.sen.concurrency3.juc.utils.condition;

import sun.font.TrueTypeFont;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Sen
 * @Date: 2019/12/16 14:26
 * @Description:
 */
public class ConditionExample1 {

    private static  final ReentrantLock lock = new ReentrantLock(true);

    private static final Condition condition = lock.newCondition();

    private static final AtomicBoolean noUser = new AtomicBoolean(true);

    private static int data = 0;

    public static void main(String[] args) {
        new Thread(()->{
            for (; ; ) {
                buildData();
            }
        }).start();
        new Thread(()->{
            for (; ; ) {
                useData();
            }
        }).start();
    }

    private static void buildData() {
        try {
            lock.lock();
            while (noUser.get())
                condition.await();
            data++;
            System.out.println("P; " + data);
            noUser.set(true);
            TimeUnit.SECONDS.sleep(1);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void useData() {
        try {
            lock.lock();
            while (!noUser.get())
                condition.await();
            System.out.println("C: " + data);
            noUser.set(false);
            TimeUnit.SECONDS.sleep(1);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
