package com.sen.concurrency1.chapter4;

import java.util.Optional;

/**
 * @Author: Sen
 * @Date: 2019/12/7 02:01
 * @Description: {@link Thread}--{@code setPriority()}测试线程权重对线程获取CPU时间片的影响
 * 结论：设置权值大小这能让该线程具有较大的机会获取CPU时间片，他们的先后完成任务顺序不一定按照所设定的
 * 权重那样的顺序完成。
 */
public class SimpleThreadApi2 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Optional.of(Thread.currentThread().getName() + "->index:" + i).ifPresent(System.out::println);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Optional.of(Thread.currentThread().getName() + "->index:" + i).ifPresent(System.out::println);
            }
        });

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Optional.of(Thread.currentThread().getName() + "->index:" + i).ifPresent(System.out::println);
            }
        });
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);
        t3.setPriority(Thread.NORM_PRIORITY);

        t1.start();
        t2.start();
        t3.start();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
