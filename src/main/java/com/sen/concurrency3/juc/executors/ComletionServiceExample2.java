package com.sen.concurrency3.juc.executors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: Sen
 * @Date: 2019/12/18 00:28
 * @Description: {@link CompletionService} 改进 {@link Future} 可以做到先完成先返回；但是还是无法做到回调
 */
public class ComletionServiceExample2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /*ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        List<Callable<Integer>> callables = Arrays.asList(
                () -> {
                    sleepSeconds(10);
                    System.out.println("callable:--->10");
                    return 10;
                }, () -> {
                    sleepSeconds(20);
                    System.out.println("callable:--->20");
                    return 20;
                });

        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        callables.forEach(completionService::submit);
        Future<Integer> future;
        while ((future = completionService.take()) != null) {
            System.out.println(future.get());
        }*/

        // 给CompletionService实现Future
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        CompletionService<Event> completionService = new ExecutorCompletionService<>(executor);

        Event event = new Event(1);
        completionService.submit(new MyTask(event), event);
        System.out.println(completionService.take().get().getResult());
    }

    private static class MyTask implements Runnable {

        private final Event event;

        private MyTask(Event event) {
            this.event = event;
        }

        @Override
        public void run() {
            sleepSeconds(3);
            event.setResult("I am successful to execute");
        }
    }

    private static class Event {

        private final int eventId;

        /**
         * 执行结果
         */
        private String result;

        public Event(int eventId) {
            this.eventId = eventId;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }

        public int getEventId() {
            return eventId;
        }
    }

    private static void sleepSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
