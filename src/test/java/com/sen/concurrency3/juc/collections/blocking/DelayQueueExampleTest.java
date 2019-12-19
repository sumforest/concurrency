package com.sen.concurrency3.juc.collections.blocking;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 * @Auther: Sen
 * @Date: 2019/12/19 19:09
 * @Description:
 */
public class DelayQueueExampleTest {

    /**
     * {@link DelayQueue#peek()}
     * 方法立即返回；
     * {@link DelayQueue#add(Delayed)}
     * {@link DelayQueue#put(Delayed)}
     * 调用的都是{@link DelayQueue#offer(Delayed)}方法
     */
    @Test
    public void testAdd() {
        DelayQueue<DelayedElement> delayQueue = DelayQueueExample.create();
        DelayedElement element = new DelayedElement("delayed1", 1000);
        assertThat(delayQueue.add(element), equalTo(true));
        assertThat(delayQueue.size(), equalTo(1));
        long startTime = System.currentTimeMillis();
        assertThat(delayQueue.peek(), equalTo(element));
        assertThat(System.currentTimeMillis() - startTime < 5, equalTo(true));
    }

    /**
     * 迭代器立即返回队列里面的值
     */
    @Test
    public void testIteratorImmediately() {
        DelayQueue<DelayedElement> delayQueue = DelayQueueExample.create();
        delayQueue.add(new DelayedElement("delayed1", 600));
        delayQueue.add(new DelayedElement("delayed2", 1200));
        delayQueue.add(new DelayedElement("delayed3", 500));
        delayQueue.add(new DelayedElement("delayed4", 2000));
        Iterator<DelayedElement> iterator = delayQueue.iterator();
        long startTime = System.currentTimeMillis();
        assertThat(iterator.next().data, equalTo("delayed3"));
        assertThat(System.currentTimeMillis() - startTime < 5, equalTo(true));
    }

    /**
     * {@link DelayQueue#remove()}方法底层调用的是{@link DelayQueue#poll()}方法，poll()返回的是队列中
     * 过期的对象，如果没有过期的则返回null
     */
    @Test(expected = NoSuchElementException.class)
    public void testRemove() {
        DelayQueue<DelayedElement> delayQueue = DelayQueueExample.create();
        delayQueue.add(new DelayedElement("delayed1", 600));
        delayQueue.add(new DelayedElement("delayed2", 1200));
        delayQueue.add(new DelayedElement("delayed3", 500));
        delayQueue.add(new DelayedElement("delayed4", 2000));
        long startTime = System.currentTimeMillis();
        assertThat(delayQueue.remove().getData(), equalTo(null));
        assertThat(System.currentTimeMillis() - startTime < 5, equalTo(true));
    }

    /**
     * {@link DelayQueue#take()}等待超时时间在获取队头的值
     * @throws InterruptedException
     */
    @Test
    public void testTake() throws InterruptedException {
        DelayQueue<DelayedElement> delayQueue = DelayQueueExample.create();
        delayQueue.add(new DelayedElement("delayed1", 600));
        delayQueue.add(new DelayedElement("delayed2", 1200));
        delayQueue.add(new DelayedElement("delayed3", 500));
        delayQueue.add(new DelayedElement("delayed4", 2000));
        long startTime = System.currentTimeMillis();
        assertThat(delayQueue.take().getData(), equalTo("delayed3"));
        assertThat(System.currentTimeMillis() - startTime > 500, equalTo(true));
    }

    /**
     * 不允许添加空值
     * @throws InterruptedException
     */
    @Test
    public void testAddNull() throws InterruptedException {
        DelayQueue<DelayedElement> delayQueue = DelayQueueExample.create();
        try {
            delayQueue.add(null);
        } catch (Exception e) {
            assertThat(e instanceof NullPointerException,equalTo(true));
        }
    }
    @Test
    public void testPoll() throws InterruptedException {
        DelayQueue<DelayedElement> delayQueue = DelayQueueExample.create();
        delayQueue.add(new DelayedElement("delayed1", 600));
        delayQueue.add(new DelayedElement("delayed2", 1200));
        delayQueue.add(new DelayedElement("delayed3", 500));
        delayQueue.add(new DelayedElement("delayed4", 2000));
        assertThat(delayQueue.poll(), nullValue());
    }
    /**
     * 过期的对象take会立即返回
     * @throws InterruptedException
     */
    @Test
    public void testTakeExpiredTime() throws InterruptedException {
        DelayQueue<DelayedElement> delayQueue = DelayQueueExample.create();
        delayQueue.add(new DelayedElement("delayed1", 600));
        delayQueue.add(new DelayedElement("delayed2", 1200));
        delayQueue.add(new DelayedElement("delayed3", 500));
        delayQueue.add(new DelayedElement("delayed4", 2000));
        TimeUnit.MILLISECONDS.sleep(600);
        System.out.println("======================");
        assertThat(delayQueue.take().getData(), equalTo("delayed3"));
    }



    private static class DelayedElement implements Delayed {

        private String data;

        private long expireTime;

        public DelayedElement(String data, long delayedTime) {
            this.data = data;
            expireTime = System.currentTimeMillis() + delayedTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return expireTime - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed delayedObj) {
            DelayedElement that = (DelayedElement) delayedObj;
            if (this.expireTime - that.expireTime > 0) {
                return 1;
            } else if (this.expireTime - that.expireTime < 0) {
                return -1;
            } else {
                return 1;
            }
        }

        public String getData() {
            return data;
        }

        public long getExpireTime() {
            return expireTime;
        }
    }
}