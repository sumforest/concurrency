package com.sen.concurrency3.juc.executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/17 16:51
 * @Description: 线程池异常处理
 */
public class ExecutorServiceExample1 {

    public static void main(String[] args) throws InterruptedException {
        // isShutdown();
        // isTerminal();
        // RunnableError();
        RunnableError2();
    }

    private static void RunnableError() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10
                ,new MyThreadFactory());
        IntStream.rangeClosed(1, 10).boxed().forEach(i ->
            executor.execute(()-> System.out.println(1/0))
         );
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("================================");
    }

    private static void RunnableError2() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10
                ,new MyThreadFactory());
        IntStream.rangeClosed(1, 10).boxed().forEach(i -> executor.execute(new RunnableTask()));
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("================================");
    }

    private static class RunnableTask extends RunnableTaskTemplate{

        @Override
        protected void done() {
            System.out.println(Thread.currentThread().getName()+ " DONE");
        }

        @Override
        protected void doExecute() {
            System.out.println(1 / 0);
        }

        @Override
        protected void doInit() {
            System.out.println(Thread.currentThread().getName() + " IN");
        }

        @Override
        protected void doError() {
            System.out.println(Thread.currentThread().getName() + " ERROR");
        }
    }

    private abstract static class RunnableTaskTemplate implements Runnable{

        @Override
        public void run() {
            try {
                this.doInit();
                this.doExecute();
                this.done();
            } catch (Throwable cause) {
                this.doError();
            }
        }

        protected abstract void done();

        protected abstract void doExecute();

        protected abstract void doInit();

        protected abstract void doError();
    }

    /**
     * 线程工程，用于线程池创建线程
     */
    private static class MyThreadFactory implements ThreadFactory{

        private final static AtomicInteger SEQ = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread t1 = new Thread(r);
            t1.setName("Thread -> " + SEQ.getAndIncrement());
            // 设置线程未捕获异常的异常处理
            t1.setUncaughtExceptionHandler((t,e)->{
                System.out.println("*********************************************");

                System.out.println(t.getName() + " execute FAILED");
                e.printStackTrace();
                System.out.println("*********************************************");
            });
            return t1;
        }
    }

    private static void isTerminal() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        executor.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(executor.isShutdown());
        System.out.println(executor.isTerminated());
        System.out.println(executor.isTerminating());
        executor.shutdown();
        System.out.println(executor.isShutdown());
        System.out.println(executor.isTerminated());
        System.out.println(executor.isTerminating());
    }

    private static void isShutdown() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(service.isShutdown());
        service.shutdown();
        System.out.println(service.isShutdown());
    }
}
