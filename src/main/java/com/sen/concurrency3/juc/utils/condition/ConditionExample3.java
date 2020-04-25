package com.sen.concurrency3.juc.utils.condition;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/16 15:17
 * @Description: 使用condition实现多线程下的多生产者消费者
 */
public class ConditionExample3 {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final Condition PRODUCE_CONDITION = LOCK.newCondition();

    private static final Condition CONSUMER_CONDITION = LOCK.newCondition();

    private static final LinkedList<Long> TIMESTAMPS = new LinkedList<>();

    private static final int MAX_QUEUE_SIZE = 100;

    public static void main(String[] args) {
        IntStream.range(0, 5).forEach(i-> new Thread(()->{
            for (; ; ) {
                produce();
            }
        }, "P: " + i).start());

        IntStream.range(0, 10).forEach(i->new Thread(()->{
            for (; ; ) {
                consume();
            }
        },"C: " + i).start());
    }

    private static void produce(){
        LOCK.lock();
        try {
            while ((TIMESTAMPS.size() > MAX_QUEUE_SIZE)) {
                PRODUCE_CONDITION.await();
            }
            long value = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " P->" + value);
            TIMESTAMPS.addLast(value);
            TimeUnit.SECONDS.sleep(1);
            CONSUMER_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    private static void consume() {
        LOCK.lock();
        try {
            while (TIMESTAMPS.isEmpty()) {
                CONSUMER_CONDITION.await();
            }
            System.out.println(Thread.currentThread().getName() + " C->" + TIMESTAMPS.removeFirst());
            TimeUnit.SECONDS.sleep(2);
            PRODUCE_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }
}
