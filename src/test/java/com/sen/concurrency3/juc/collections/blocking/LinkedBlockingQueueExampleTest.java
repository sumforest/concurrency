package com.sen.concurrency3.juc.collections.blocking;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * @Auther: Sen
 * @Date: 2019/12/19 17:13
 * @Description:
 */
public class LinkedBlockingQueueExampleTest {

    private LinkedBlockingQueueExample example;
    @Before
    public void setUp() {
        example = new LinkedBlockingQueueExample();
    }

    @After
    public void tearDown(){
        example = null;
    }

    @Test
    public void testOffer() {
        LinkedBlockingQueue<String> queue = example.create(3);
        assertThat(queue.offer("Hello1"), equalTo(true));
        assertThat(queue.offer("Hello2"), equalTo(true));
        assertThat(queue.offer("Hello3"), equalTo(true));
        assertThat(queue.offer("Hello4"), equalTo(false));
    }

    /**
     * 队列满了{@link LinkedBlockingQueue#put(Object)}将会阻塞
     * @throws InterruptedException
     */
    @Test
    public void testPut() throws InterruptedException {
        LinkedBlockingQueue<String> queue = example.create(3);
        queue.put("Hello1");
        queue.put("Hello2");
        queue.put("Hello3");
        queue.put("Hello4");
        fail("should not run there");
    }
}