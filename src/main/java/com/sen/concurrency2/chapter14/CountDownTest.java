package com.sen.concurrency2.chapter14;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/10 22:52
 * @Description:
 */
public class CountDownTest {

    private final static CountDown latch = new CountDown(5);

    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        System.out.println("开启第一阶段工作");
        IntStream.rangeClosed(1, 5).forEach(i -> new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is working");
            try {
                Thread.sleep(random.nextInt(1000));
                latch.down();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, String.valueOf(i)).start());

        latch.await();
        System.out.println("第一阶段任务完成，开始第二阶段任务");
        System.out.println("..............");
        System.out.println("Finished");
    }
}
