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
 * @Description: {@link Condition}它用来替代传统的Object的wait()、notify()实现线程间的协作，相比使用
 * Object的wait()、notify()，使用Condition的await()、signal()这种方式实现线程间协作更加安全和高效。因
 * 此通常来说比较推荐使用Condition，阻塞队列实际上是使用了Condition来模拟线程间协作。
 */
public class ConditionExample1 {

    private static  final ReentrantLock lock = new ReentrantLock(true);

    private static final Condition condition = lock.newCondition();

    /**
     * 标记数据状态，true表示已准备好，false表示未准备好
     */
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
            // 数据已经准备好，休眠
            while (noUser.get()) {
                condition.await();
            }
            data++;
            System.out.println("P: " + data);
            // 准备好数据好，标记为“已准备”
            noUser.set(true);
            TimeUnit.SECONDS.sleep(1);
            // 通知消费者
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
            // 数据未准备好，休眠
            while (!noUser.get()) {
                condition.await();
            }
            System.out.println("C: " + data);
            // 使用完数据，标记为“未准备”
            noUser.set(false);
            TimeUnit.SECONDS.sleep(1);
            // 通知生产者
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
