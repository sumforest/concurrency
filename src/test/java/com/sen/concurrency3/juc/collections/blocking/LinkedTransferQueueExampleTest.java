package com.sen.concurrency3.juc.collections.blocking;

import org.junit.Test;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * @Author: Sen
 * @Date: 2019/12/19 22:35
 * @Description:
 */
public class LinkedTransferQueueExampleTest {

    /**
     * 当没有消费者消费transferQueue时tryTransfer()返回false，添加元素失败
     */
    @Test
    public void testTryTransfer() {
        LinkedTransferQueue<String> transferQueue = LinkedTransferQueueExample.create();
        assertThat(transferQueue.tryTransfer("hello"), equalTo(false));
        assertThat(transferQueue.size(), equalTo(0));
    }

    /**
     * {@link LinkedTransferQueue#transfer(Object)}
     * 没有消费者时一直阻塞，直到它的元素被消费。
     * @throws InterruptedException
     */
    @Test
    public void testTransfer() throws InterruptedException {
        LinkedTransferQueue<String> transferQueue = LinkedTransferQueueExample.create();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(transferQueue::take, 1, TimeUnit.SECONDS);
        TimeUnit.MILLISECONDS.sleep(20);
        transferQueue.transfer("hello");
        assertThat(transferQueue.size(), equalTo(0));
    }

    /**
     * {@link LinkedTransferQueue#add(Object)}
     * {@link LinkedTransferQueue#put(Object)} are never block.
     */
    @Test
    public void testPut() {
        LinkedTransferQueue<String> transferQueue = LinkedTransferQueueExample.create();
        transferQueue.put("hello");
        assertThat(transferQueue.size(), equalTo(1));
    }

    @Test
    public void testGetWaitingConsumer_HasWaitingConsumer() throws InterruptedException {
        LinkedTransferQueue<String> transferQueue = LinkedTransferQueueExample.create();

        assertThat(transferQueue.hasWaitingConsumer(), equalTo(false));
        assertThat(transferQueue.getWaitingConsumerCount(), equalTo(0));

        ExecutorService executorService = Executors.newCachedThreadPool();
        IntStream.rangeClosed(1, 5).boxed().forEach((i)->executorService.submit(transferQueue::take));

        TimeUnit.MILLISECONDS.sleep(20);
        assertThat(transferQueue.hasWaitingConsumer(), equalTo(true));
        assertThat(transferQueue.getWaitingConsumerCount(), equalTo(5));

        IntStream.rangeClosed(1, 5).boxed().map(String::valueOf)
                .forEach((s)->executorService.submit(()-> {
                    try {
                        transferQueue.transfer(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));

        TimeUnit.MILLISECONDS.sleep(20);
        assertThat(transferQueue.hasWaitingConsumer(), equalTo(false));
        assertThat(transferQueue.getWaitingConsumerCount(), equalTo(0));
    }

}