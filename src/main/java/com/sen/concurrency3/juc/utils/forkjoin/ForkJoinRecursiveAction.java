package com.sen.concurrency3.juc.utils.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/16 17:55
 * @Description:
 */
public class ForkJoinRecursiveAction {

    private final static AtomicInteger sum = new AtomicInteger(0);

    private final static int MAX_DEFAULT = 3;

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new CalculateAction(1, 10));
        forkJoinPool.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println(sum);
    }

    private static class CalculateAction extends RecursiveAction{

        private final int start;

        private final int end;

        public CalculateAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if ((end - start) <= MAX_DEFAULT) {
                sum.getAndAdd(IntStream.rangeClosed(start, end).sum());
            } else {
                int middle = (start + end) / 2;
                CalculateAction leftAction = new CalculateAction(start, middle);
                CalculateAction rightAction = new CalculateAction(middle + 1, end);
                leftAction.fork();
                rightAction.fork();
            }
        }
    }
}
