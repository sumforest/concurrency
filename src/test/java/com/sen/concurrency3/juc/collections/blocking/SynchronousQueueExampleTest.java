package com.sen.concurrency3.juc.collections.blocking;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * @Auther: Sen
 * @Date: 2019/12/19 17:42
 * @Description:
 */
public class SynchronousQueueExampleTest {

    private SynchronousQueueExample example;

    @Before
    public void setUp(){
        example = new SynchronousQueueExample();
    }

    @After
    public void tearDown(){
        example = null;
    }

    /**
     * @link SynchronousQueue} 是一个transfer queue 没有容量
     * 生产者消费者的另一种实现
     *                 no cache(没有容量即没有缓存)
     * produce-----> productuon------->consumer
     * 没有线程调用take方法时，往queue里面加元素会失败
     */
    @Test(expected = IllegalStateException.class)
    public void testAdd(){
        SynchronousQueue<String> transferQueue = example.create();
        assertThat(transferQueue.add("Hello1"), equalTo(true));
    }

    @Test
    public void testAddSuccess() throws InterruptedException {
        SynchronousQueue<String> transferQueue = example.create();
        ExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.submit(()->{
            try {
                transferQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        TimeUnit.MILLISECONDS.sleep(20);
        assertThat(transferQueue.add("Hello1"), equalTo(true));
        service.shutdown();
    }

    @Test
    public void testPut() throws InterruptedException {
        SynchronousQueue<String> transferQueue = example.create();
        transferQueue.put("Hello1");
        fail("should run there");
    }

    @Test
    public void testOffer() throws InterruptedException {
        SynchronousQueue<String> transferQueue = example.create();
        assertThat(transferQueue.offer("Hello1"), equalTo(false));
    }

}