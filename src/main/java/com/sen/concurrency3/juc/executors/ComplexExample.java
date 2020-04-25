package com.sen.concurrency3.juc.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/18 02:44
 * @Description: 场景: 1.解决调用{@link ExecutorService#shutdownNow()}获取的未执行任务队列不准确
 * 2.解决任务队列中的返回对象是ExecutorCompletionService$QueueingFuture@52cc8049并不是们定义的Runnable
 */
public class ComplexExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        // 调用shutdownNow()立即停止线程池时，获取还没有完成的任务
        List<MyTask> tasks = IntStream
                .rangeClosed(1, 5)
                .boxed().map(MyTask::new)
                .collect(Collectors.toList());

        tasks.forEach(completionService::submit);
        TimeUnit.MILLISECONDS.sleep(3000);
        executorService.shutdownNow();

        List<MyTask> failedTasks = tasks
                .stream()
                .filter(task -> !task.success)
                .collect(Collectors.toList());

        System.out.println(failedTasks.size());
        System.out.println(failedTasks);

        // 出错演示
        /*List<Runnable> runnables = IntStream.rangeClosed(1, 5)
                .boxed()
                .map(ComplexExample::task)
                .collect(Collectors.toList());

        runnables.forEach(r -> completionService.submit(r,"Done"));

        TimeUnit.MILLISECONDS.sleep(500);
        List<Runnable> runnableList = executorService.shutdownNow();
        System.out.println(runnableList.size());
        System.out.println(runnableList);
        Future<?> future;
        while ((future = completionService.take()) != null) {
            System.out.println(future.get());
        }*/
    }

    private static class MyTask implements Callable<String> {
        private boolean success = false;
        private final int no;

        private MyTask(int no) {
            this.no = no;
        }

        @Override
        public String call() throws Exception {
            System.out.printf("The task [%d] Start\n", no);
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
            System.out.printf("The task [%d] Done\n", no);
            // System.out.printf("The task [%d] Error\n", no);
            this.success = true;
            return "Success";
        }

        public boolean isSuccess() {
            return success;
        }
    }

    private static Runnable task(int i) {
        return () -> {
            System.out.printf("The task [%d] Start\n", i);
            try {
                // 线程独有的随机数
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                System.out.printf("The task [%d] Done\n", i);
            } catch (InterruptedException e) {
                System.out.printf("The task [%d] Error\n", i);
            }
        };
    }
}
