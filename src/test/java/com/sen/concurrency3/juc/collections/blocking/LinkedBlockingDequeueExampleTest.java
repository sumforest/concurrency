package com.sen.concurrency3.juc.collections.blocking;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * @Auther: Sen
 * @Date: 2019/12/19 21:40
 * @Description:
 */
public class LinkedBlockingDequeueExampleTest {

    @Test
    public void testAddFirst(){
        LinkedBlockingDeque<String> deque = LinkedBlockingDequeueExample.create();
        deque.addFirst("Java");
        deque.addFirst("Scala");
        assertThat(deque.removeFirst(), equalTo("Scala"));
        assertThat(deque.removeFirst(), equalTo("Java"));
    }

    @Test
    public void testAdd(){
        LinkedBlockingDeque<String> deque = LinkedBlockingDequeueExample.create();
        deque.add("Java");
        deque.add("Scala");
        assertThat(deque.removeFirst(), equalTo("Java"));
        assertThat(deque.removeFirst(), equalTo("Scala"));
    }

    @Test
    public void testTake() throws InterruptedException {
        LinkedBlockingDeque<String> deque = LinkedBlockingDequeueExample.create();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> deque.add("hello"), 1, TimeUnit.SECONDS);
        executorService.shutdown();
        long startTime = System.currentTimeMillis();
        deque.take();
        assertThat(System.currentTimeMillis() - startTime >= 1000, equalTo(true));
    }

}