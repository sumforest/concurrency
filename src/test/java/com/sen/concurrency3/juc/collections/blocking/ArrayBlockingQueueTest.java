package com.sen.concurrency3.juc.collections.blocking;


import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @Auther: Sen
 * @Date: 2019/12/19 15:08
 * @Description:
 */
public class ArrayBlockingQueueTest {

    private ArrayBlockingQueueExample<String> example;

    private ArrayBlockingQueue<String> blockingQueue;

    @Before
    public void newInstance() {
        example = new ArrayBlockingQueueExample<>();
        blockingQueue = example.create(5);
    }

    @After
    public void releaseSources() {
        example = null;
        blockingQueue = null;
    }

    @Test
    public void testAdd() {
        assertThat(blockingQueue.add("Hello1"), equalTo(true));
        assertThat(blockingQueue.add("Hello2"), equalTo(true));
        assertThat(blockingQueue.add("Hello3"), equalTo(true));
        assertThat(blockingQueue.add("Hello4"), equalTo(true));
        assertThat(blockingQueue.add("Hello5"), equalTo(true));
        assertThat(blockingQueue.size(), equalTo(5));
    }

    /**
     * 超过queue容量抛异常
     */
    @Test(expected = IllegalStateException.class)
    public void testAddNoSpace() {
        blockingQueue.add("Hello1");
        blockingQueue.add("Hello2");
        blockingQueue.add("Hello3");
        blockingQueue.add("Hello4");
        blockingQueue.add("Hello5");
        blockingQueue.add("Hello6");
        assertThat(blockingQueue.size(), equalTo(6));
    }

    @Test
    public void testPutMethod() throws InterruptedException {
        blockingQueue.put("Hello1");
        blockingQueue.put("Hello2");
        blockingQueue.put("Hello3");
        blockingQueue.put("Hello4");
        blockingQueue.put("Hello5");
        assertThat(blockingQueue.size(), equalTo(5));
    }

    /**
     * 超过queue容量阻塞，直到队列被消费，可打断
     *
     * @throws InterruptedException
     */
    @Test
    public void testPutMethodNoSpace() throws InterruptedException {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(() -> {
            try {
                assertThat(blockingQueue.take(), equalTo("Hello1"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);
        TimeUnit.MILLISECONDS.sleep(20);
        blockingQueue.put("Hello1");
        blockingQueue.put("Hello2");
        blockingQueue.put("Hello3");
        blockingQueue.put("Hello4");
        blockingQueue.put("Hello5");
        blockingQueue.put("Hello6");
        assertThat(blockingQueue.size(), equalTo(5));
    }

    @Test
    public void testOfferMethod() {
        assertThat(blockingQueue.offer("Hello1"), equalTo(true));
        assertThat(blockingQueue.offer("Hello2"), equalTo(true));
        assertThat(blockingQueue.offer("Hello3"), equalTo(true));
        assertThat(blockingQueue.offer("Hello4"), equalTo(true));
        assertThat(blockingQueue.offer("Hello5"), equalTo(true));
        assertThat(blockingQueue.size(), equalTo(5));
    }

    /**
     * 尝试插入元素，成果返回true；失败直接返回false；
     */
    @Test
    public void testOfferMethodNoSpace() {
        assertThat(blockingQueue.offer("Hello1"), equalTo(true));
        assertThat(blockingQueue.offer("Hello2"), equalTo(true));
        assertThat(blockingQueue.offer("Hello3"), equalTo(true));
        assertThat(blockingQueue.offer("Hello4"), equalTo(true));
        assertThat(blockingQueue.offer("Hello5"), equalTo(true));
        assertThat(blockingQueue.offer("Hello6"), equalTo(false));
        assertThat(blockingQueue.size(), equalTo(5));
    }

    /**
     * queue为空的时候一直阻塞
     * @throws InterruptedException
     */
    @Test
    public void testTakeMethod() throws InterruptedException {
        assertThat(blockingQueue.offer("Hello1"), equalTo(true));
        assertThat(blockingQueue.offer("Hello2"), equalTo(true));
        assertThat(blockingQueue.take(), equalTo("Hello1"));
        assertThat(blockingQueue.take(), equalTo("Hello2"));
        // assertThat(blockingQueue.take(), equalTo(null));
    }

    /**
     * 取出队列的队头，不阻塞
     * @throws InterruptedException
     */
    @Test
    public void testPollMethod() throws InterruptedException {
        assertThat(blockingQueue.offer("Hello1"), equalTo(true));
        assertThat(blockingQueue.offer("Hello2"), equalTo(true));
        assertThat(blockingQueue.poll(), equalTo("Hello1"));
        assertThat(blockingQueue.poll(), equalTo("Hello2"));
        assertThat(blockingQueue.poll(), equalTo(null));
    }

    @Test
    public void testPeekMethod() throws InterruptedException {
        assertThat(blockingQueue.offer("Hello1"), equalTo(true));
        assertThat(blockingQueue.peek(), equalTo("Hello1"));
        assertThat(blockingQueue.peek(), equalTo("Hello1"));
        assertThat(blockingQueue.peek(), equalTo("Hello1"));
        blockingQueue.clear();
        assertThat(blockingQueue.peek(), equalTo(null));
    }

    @Test
    public void testDrainTo() throws InterruptedException {
        assertThat(blockingQueue.offer("Hello1"), equalTo(true));
        assertThat(blockingQueue.offer("Hello2"), equalTo(true));
        List<String> list = new ArrayList<>();
        assertThat(blockingQueue.drainTo(list), equalTo(2));
    }
}
