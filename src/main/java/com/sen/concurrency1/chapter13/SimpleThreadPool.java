package com.sen.concurrency1.chapter13;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/8 16:53
 * @Description: 自定义简单线程池
 */
public class SimpleThreadPool extends Thread {

    /**
     * 线程池的线程数
     */
    private int size;

    /**
     * 任务数
     */
    private int taskSize;

    /**
     * 最小线程数
     */
    private int min;

    /**
     * 活跃线程数
     */
    private int active;

    /**
     * 最大线程数
     */
    private int max;

    private final static int DEFAULT_TASK_SIZE = 2000;

    private final static ThreadGroup THREAD_GROUP = new ThreadGroup("SimpleThreadPool");

    private final static List<Worker> THREAD_QUEUE = new ArrayList<>();

    /**
     * 拒绝策略
     */
    private Refuse refuse;

    private static volatile int sqe = 0;

    private boolean isShutdown = false;

    /**
     * 默认拒绝策略
     */
    public final static Refuse DEFAULT_REFUSE_STRATEGY = () -> {
        throw new OverMaxTaskSizeException("over max task count");
    };

    /**
     * 任务队列
     */
    private final static LinkedList<Runnable> tasks = new LinkedList<>();

    public SimpleThreadPool() {
        this(4, 8, 12, DEFAULT_TASK_SIZE, DEFAULT_REFUSE_STRATEGY);
    }

    public SimpleThreadPool(int min, int active, int max, int taskSize, Refuse refuse) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.taskSize = taskSize;
        this.refuse = refuse;
        this.init();
    }

    private static class OverMaxTaskSizeException extends IllegalStateException {
        public OverMaxTaskSizeException(String message) {
            super(message);
        }
    }

    public interface Refuse {

        /**
         * 拒绝策略
         *
         * @throws OverMaxTaskSizeException 超过最大任务队列抛出此异常
         */
        void refuse() throws OverMaxTaskSizeException;
    }

    /**
     * 提交任务
     *
     * @param runnable 任务
     */
    public void submit(Runnable runnable) {
        if (isShutdown) {
            //线程池关闭后再提交任务抛异常
            throw new IllegalStateException("current thread pool have been shutdown");
        }
        synchronized (tasks) {
            //超过最大任务队列所能容纳的数量时执行决绝策略
            if (tasks.size() > taskSize) {
                refuse.refuse();
            }
            //添加到任务队列
            tasks.addLast(runnable);
            //唤醒线程池的线程执行提交任务
            tasks.notifyAll();
        }
    }

    /**
     * 此线程用于管理线程
     */
    @Override
    public void run() {
        while (!isShutdown) {
            System.out.printf("ThreadPool: min:%d,active:%d,max:%d,currentThread:%d,taskSize:%d\n",
                    min, active, max, THREAD_QUEUE.size(), tasks.size());
            try {
                Thread.sleep(5000);
                //扩展线程数量到active
                if (tasks.size() > active && size < active) {
                    for (int i = size; i < active; i++) {
                        createThread();
                    }
                    size = active;
                    System.out.println("ThreadPool incremented");
                    //任务数 > active并且线程数还没到最大时，扩容线程池到max
                } else if (tasks.size() > max && size < max) {
                    for (int i = size; i < max; i++) {
                        createThread();
                    }
                    size = max;
                    System.out.println("ThreadPool incremented");
                }
                //当任务队列为空 && 线程数大于活跃数池 减少线程池的线程数
                //加锁，再进行缩容时不允许提交任务
                synchronized (THREAD_QUEUE) {
                    if (tasks.isEmpty() && size > active) {
                        int releaseSize = size - active;
                        System.out.println("===========ThreadPool decremented");
                        //迭代器，边遍历边删除不会导致异常
                        for (Iterator<Worker> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            Worker worker = it.next();
                            //等待线程工作完成
                            while (worker.status != ThreadStatus.BLOCK) {
                                System.out.println("Wait to thread block...");
                                Thread.sleep(20);
                            }
                            if (releaseSize <= 0) {
                                break;
                            }
                            worker.interrupt();
                            worker.close();
                            it.remove();
                            releaseSize--;
                        }
                        size = active;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭线程池
     * @throws InterruptedException 中断异常
     */
    public void shutdown() throws InterruptedException {
        //判断任务是否执行完成，等待任务全部完成
        while (tasks.size() > 0) {
            Thread.sleep(50);
        }
        synchronized (THREAD_QUEUE) {
            int value = THREAD_QUEUE.size();
            while (value > 0) {
                for (Worker worker : THREAD_QUEUE) {
                    //判断当前线程是否是阻塞状态，为阻塞状态停止
                    if (worker.status == ThreadStatus.BLOCK) {
                        worker.interrupt();
                        worker.close();
                        value--;
                    } else {
                        Thread.sleep(10);
                    }
                }
            }
            isShutdown = true;
        }
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    /**
     * 初始化线程池
     */
    private void init() {
        for (int i = 0; i < min; i++) {
            createThread();
        }
        size = min;
        this.start();
    }

    /**
     * 创建并启动线程
     */
    private void createThread() {
        Worker worker = new Worker(THREAD_GROUP, THREAD_GROUP.getName() + "->" + sqe++);
        THREAD_QUEUE.add(worker);
        worker.start();
    }

    private enum ThreadStatus {
        /**
         * 空闲
         */
        FREE,
        /**
         * 阻塞
         */
        BLOCK,
        /**
         * 死亡
         */
        DEAD,
        /**
         * 运行
         */
        RUNNING
    }

    private static class Worker extends Thread {

        private ThreadStatus status = ThreadStatus.FREE;

        public Worker(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            OUTER:
            while (status != ThreadStatus.DEAD) {
                Runnable runnable;
                synchronized (tasks) {
                    //任务队列空进入阻塞
                    while (tasks.isEmpty()) {
                        status = ThreadStatus.BLOCK;
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            //阻塞状态的线程被打断判断生命周期是否结束
                            break OUTER;
                        }
                    }
                    //提取任务
                    runnable = tasks.removeFirst();
                }
                if (runnable != null) {
                    status = ThreadStatus.RUNNING;
                    //执行任务
                    runnable.run();
                    status = ThreadStatus.FREE;
                }
            }
        }

        public ThreadStatus getStatus() {
            return status;
        }

        /**
         * 线程完成任务后设置为DEAD状态
         */
        public void close() {
            status = ThreadStatus.DEAD;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        SimpleThreadPool threadPool = new SimpleThreadPool();

        IntStream.rangeClosed(0, 40).forEach(i -> threadPool.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " create task_" + i);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end task_" + i);
        }));

        Thread.sleep(40000);
        threadPool.shutdown();
        // threadPool.submit(()-> System.out.println("hello"));
        // System.out.println(threadPool.isShutdown());
    }
}
