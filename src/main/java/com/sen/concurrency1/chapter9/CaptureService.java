package com.sen.concurrency1.chapter9;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @Author: Sen
 * @Date: 2019/12/8 00:19
 * @Description: 指定数量的线程进行数据采集模拟,避免创建过多线程导致效率低下;后面可以用
 * 线程池解决
 */
public class CaptureService {

    /**
     * 用于加锁和控制线程大小
     */
    private final static LinkedList<Control> CONTROL = new LinkedList<>();

    /**
     * 最大并发的线程数量
     */
    private final static int MAX_THREAD = 5;

    public static void main(String[] args) {
        List<Thread> queue = new ArrayList<>();
        Stream.of("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10").
                map(CaptureService::createCaptureThread).forEach(thread -> {
            thread.start();
            queue.add(thread);
        });

        queue.forEach(t-> {
            try {
                //让父线程等待工作线程完成
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Optional.of("All capture task work finished").ifPresent(System.out::println);
    }

    /**
     * 创建采集工作线程
     * @param name 线程名
     * @return 采集线程
     */
    private static Thread createCaptureThread(String name) {
        return new Thread(() -> {
            synchronized (CONTROL){
                Optional.of(Thread.currentThread().getName() + " ready to capture data->ready")
                        .ifPresent(System.out::println);
                while (CONTROL.size() >= MAX_THREAD) {
                    //等待并释放锁
                    try {
                        CONTROL.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CONTROL.addLast(new Control());
            }
            Optional.of(Thread.currentThread().getName() + " is working->working")
                    .ifPresent(System.out::println);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Optional.of(Thread.currentThread().getName() + " finish to capture data->end")
                    .ifPresent(System.out::println);
            CONTROL.removeFirst();
            //一个工作线程完成后唤醒在创建任务阻塞的线程
            synchronized (CONTROL) {
                CONTROL.notifyAll();
            }
        }, name);
    }

    private static class Control {

    }
}
