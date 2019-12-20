package com.sen.concurrency3.juc.collections.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Auther: Sen
 * @Date: 2019/12/20 14:31
 * @Description:
 */
public class CopyOnWriteArrayListExample {

    /**
     * 适合都多写少的情况
     * @param args
     */
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    }
}
