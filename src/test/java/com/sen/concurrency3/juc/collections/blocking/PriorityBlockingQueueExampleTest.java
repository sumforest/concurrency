package com.sen.concurrency3.juc.collections.blocking;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @Author: Sen
 * @Date: 2019/12/19 16:27
 * @Description:
 */
public class PriorityBlockingQueueExampleTest {

    private PriorityBlockingQueueExample example;

    @Before
    public void setUp(){
        example = new PriorityBlockingQueueExample();
    }

    @After
    public void tearDown(){
        example = null;
    }

    /**
     * {@link PriorityBlockingQueue#add(Object)}
     * {@link PriorityBlockingQueue#put(Object)}
     * {@link PriorityBlockingQueue#offer(Object)}
     * 三个方法都是一样的都add()和put()都是通过调offer()方法实现的。
     */
    @Test
    public void testAdd(){
        PriorityBlockingQueue<String> queue = example.create(5);
        assertThat(queue.add("Hello1"),equalTo(true));
        assertThat(queue.add("Hello2"),equalTo(true));
        assertThat(queue.add("Hello3"),equalTo(true));
        assertThat(queue.add("Hello4"),equalTo(true));
        assertThat(queue.add("Hello5"),equalTo(true));
        assertThat(queue.add("Hello6"),equalTo(true));
        assertThat(queue.size(), equalTo(6));
    }

    @Test
    public void testPeek(){
        PriorityBlockingQueue<String> queue = example.create(5);
        assertThat(queue.add("Hello1"),equalTo(true));
        assertThat(queue.add("Hello2"),equalTo(true));
        assertThat(queue.peek(), equalTo("Hello1"));
        assertThat(queue.size(), equalTo(2));

        assertThat(queue.element(), equalTo("Hello1"));
        assertThat(queue.size(), equalTo(2));
    }
    @Test
    public void testPoll_Take_Remove() throws InterruptedException {
        PriorityBlockingQueue<String> queue = example.create(5);
        assertThat(queue.add("Hello5"),equalTo(true));
        assertThat(queue.add("Hello2"),equalTo(true));
        assertThat(queue.add("Hello3"),equalTo(true));

        assertThat(queue.poll(), equalTo("Hello2"));
        assertThat(queue.poll(), equalTo("Hello3"));
        assertThat(queue.size(), equalTo(1));

        assertThat(queue.take(), equalTo("Hello5"));
        assertThat(queue.size(), equalTo(0));

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(() -> queue.add("NewWorld"), 1, TimeUnit.SECONDS);
        service.shutdown();
        assertThat(queue.take(), equalTo("NewWorld"));

        assertThat(queue.add("Hello6"),equalTo(true));
        assertThat(queue.remove(), equalTo("Hello6"));
    }

    @Test(expected = ClassCastException.class)
    public void testWithout_Comparator_Comparable(){
        PriorityBlockingQueue<User> queue = example.create(5);
        queue.add(new User());
        fail("The progress should not to run here");
    }

    @Test
    public void testWithout_ComparatorHasComparable(){
        PriorityBlockingQueue<User> queue = example.create(5);
        queue.add(new User());
    }

    @Test
    public void testHasComparatorWithoutComparable(){
        PriorityBlockingQueue<UserWithoutComparable> queue = example.create(5, Comparator.comparingInt(Object::hashCode));
        queue.add(new UserWithoutComparable());
    }

    private static class UserWithoutComparable{

    }

    private static class User implements Comparable{

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }
}