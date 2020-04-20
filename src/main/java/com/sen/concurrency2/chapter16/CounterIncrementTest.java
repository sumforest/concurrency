package com.sen.concurrency2.chapter16;

/**
 * @Author: Sen
 * @Date: 2019/12/11 00:00
 * @Description: 二阶段关闭线程设计模式：在关闭线程前做释放资源的动作
 */
public class CounterIncrementTest {

    public static void main(String[] args) throws InterruptedException {
        CounterIncrement counterIncrement = new CounterIncrement();
        counterIncrement.start();
        Thread.sleep(5000);
        counterIncrement.shutdown();
    }
}
