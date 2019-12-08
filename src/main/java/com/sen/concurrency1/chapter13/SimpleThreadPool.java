package com.sen.concurrency1.chapter13;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 16:53
 * @Description: 自定义简单线程池
 */
public class SimpleThreadPool extends Thread{

    private int size;

    private int taskSize;

    private int min;

    private int active;

    private int max;

    private final static int DEFAULT_TASK_SIZE = 2000;

    private final static ThreadGroup THREAD_GROUP = new ThreadGroup("SimpleThreadPool");

    private final static List<Worker> THREAD_QUEUE = new ArrayList<>();

    private Refuse refuse;

    private static volatile int sqe = 0;

    private boolean isShutdown = false;

    public final static Refuse DEFAULT_REFUSE_STRATEGY = ()->{
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
        this.taskSize = taskSize;
        this.refuse = refuse;
        this.min = min;
        this.active = active;
        this.max = max;
        this.init();
    }

    private static class OverMaxTaskSizeException extends IllegalStateException{
        public OverMaxTaskSizeException(String message) {
            super(message);
        }
    }

    public interface Refuse{
        void refuse() throws OverMaxTaskSizeException;
    }

    public void submit(Runnable runnable) {
        if (isShutdown)
            throw new IllegalStateException("current thread pool have been shutdown");
        synchronized (tasks) {
            if (tasks.size() > taskSize)
                refuse.refuse();
            tasks.addLast(runnable);
            tasks.notifyAll();
        }
    }

    @Override
    public void run() {
        while (!isShutdown) {
            System.out.printf("ThreadPool: min:%d,active:%d,max:%d,currentThread:%d,taskSize:%d\n",
                    min, active, max, THREAD_QUEUE.size(), tasks.size());
            try {
                Thread.sleep(5000);
                if (tasks.size() > active && size < active) {
                    for (int i = size; i < active; i++) {
                        createThread();
                    }
                    size = active;
                    System.out.println("ThreadPool incremented");
                } else if (tasks.size() > max && size < max) {
                    for (int i = size; i < max; i++) {
                        createThread();
                    }
                    size = max;
                    System.out.println("ThreadPool incremented");
                }
                synchronized (THREAD_QUEUE) {
                    if (tasks.isEmpty() && size > active) {
                        int releaseSize = size - active;
                        System.out.println("===========ThreadPool decremented");
                        for (Iterator<Worker> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            Worker worker = it.next();
                            while (worker.status != ThreadStatus.BLOCK) {
                                System.out.println("Wait to thread block...");
                                Thread.sleep(20);
                            }
                            if (releaseSize <= 0)
                                break;
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

    public void shutdown() throws InterruptedException {
        //判断任务是否执行完成
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
                    }else {
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

    private void init() {
        for (int i = 0; i < min; i++) {
            createThread();
        }
        size = min;
        this.start();
    }

    private void createThread(){
        Worker worker = new Worker(THREAD_GROUP,THREAD_GROUP.getName() + "->"+sqe++);
        THREAD_QUEUE.add(worker);
        worker.start();
    }

    private enum ThreadStatus {
        FREE, BLOCK, DEAD, RUNNING
    }

    private static  class Worker extends Thread {

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
                    while (tasks.isEmpty()) {
                        status = ThreadStatus.BLOCK;
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                    runnable = tasks.removeFirst();
                }
                if (runnable != null) {
                    status = ThreadStatus.RUNNING;
                    runnable.run();
                    status = ThreadStatus.FREE;
                }
            }
        }

        public ThreadStatus getStatus() {
            return status;
        }

        public void close() {
            status = ThreadStatus.DEAD;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool threadPool = new SimpleThreadPool();
        IntStream.rangeClosed(0, 40).forEach(i->threadPool.submit(()->{
            System.out.println(Thread.currentThread().getName() + " create task" + i);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end task" + i);
        }));

        Thread.sleep(40000);
        threadPool.shutdown();
        // threadPool.submit(()-> System.out.println("hello"));
        // System.out.println(threadPool.isShutdown());
    }
}
