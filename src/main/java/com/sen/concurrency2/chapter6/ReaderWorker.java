package com.sen.concurrency2.chapter6;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author: Sen
 * @Date: 2019/12/9 20:39
 * @Description: 读者--读线程
 */
public class ReaderWorker extends Thread{

    private SharedData data;

    public ReaderWorker(SharedData data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (true){
            char[] chars = data.readData();
            System.out.println(Thread.currentThread().getName() + " read " + Arrays.toString(chars));
        }
    }
}
