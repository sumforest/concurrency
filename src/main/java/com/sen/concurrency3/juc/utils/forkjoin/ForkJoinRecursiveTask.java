package com.sen.concurrency3.juc.utils.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/16 17:02
 * @Description:
 */
public class ForkJoinRecursiveTask {


    private static final int MAX_DEFAULT = 3;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> result = forkJoinPool.submit(new CalculateRecursiveTask(1, 1000));
        System.out.println(result.get());
    }

    private static class CalculateRecursiveTask extends RecursiveTask<Integer> {

        private final int start;
        private final int end;

        public CalculateRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if ((end - start) <= MAX_DEFAULT) {
                return IntStream.rangeClosed(start, end).sum();
            } else {
                int middle = (end + start) / 2;
                CalculateRecursiveTask left = new CalculateRecursiveTask(start, middle);
                CalculateRecursiveTask right = new CalculateRecursiveTask(middle + 1, end);
                left.fork();
                right.fork();
                return left.join() + right.join();
            }
        }
    }
}
