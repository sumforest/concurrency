package com.sen.concurrency3.juc.collections.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: Sen
 * @Date: 2019/12/20 14:31
 * @Description:
 */
public class CopyOnWriteArrayListExample {

    /**
     * 适合读多写少的情况；写入的时候把原来的数组复制一份再进行操作，当写操作完成之后再将指针指向复制出来的数组，
     * 在进行写操作的过程中可能会导致无法读取新的数据
     * @param args
     */
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    }
}
