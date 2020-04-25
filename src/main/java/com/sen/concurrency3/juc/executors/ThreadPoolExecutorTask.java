package com.sen.concurrency3.juc.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/17 00:58
 * @Description: 停止线程池
 */
public class ThreadPoolExecutorTask {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = new ThreadPoolExecutor(10, 20
                ,30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)
                , Thread::new, new ThreadPoolExecutor.AbortPolicy());

        IntStream.rangeClosed(1, 20).boxed().forEach(i-> executor.execute(()-> {
            try {
                System.out.println(Thread.currentThread().getName() + " in");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " out");
               /* while (true) {

                }*/
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }));

        TimeUnit.SECONDS.sleep(1);
        /**
         * 1.打断所有空闲线程
         * 2.等待所有的线程工作完成
         * 3.所有空闲线程会退出
         * （没有异常抛出无法打断）
         */
        // executor.shutdown();
        /**
         * 1.先排干线程池的任务队列
         * 2.打断所有队列
         * （没有异常抛出无法打断）
         */
        List<Runnable> runnables = executor.shutdownNow();
        System.out.println(runnables);
        System.out.println(runnables.size());
        executor.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("===================All worker finished=================");
    }
}
