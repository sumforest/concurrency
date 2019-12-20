package com.sen.concurrency3.juc.collections.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Auther: Sen
 * @Date: 2019/12/20 13:55
 * @Description:
 */
public class ConcurrentLinkedQueueExample {

    /**
     * 当需要拿集合是否作为循环条件时首选{@link ConcurrentLinkedQueue#isEmpty()}它的判断条件是头不为空则不为空
     * 效率高，而{@link ConcurrentLinkedQueue#size()}作为循环条件每次移除一个元素时遍历一遍集合，效率会很低
     * @param args
     */
    public static void main(String[] args) {
        ConcurrentLinkedQueue<Long> linkedQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 100000; i++) {
            linkedQueue.add(System.nanoTime());
        }

        long statTime = System.currentTimeMillis();
        //花费时间343ms
        while (!linkedQueue.isEmpty()) {
            System.out.println(linkedQueue.poll());
        }
        //花费时间14002ms
       /* while (linkedQueue.size() > 0) {
            System.out.println(linkedQueue.poll());
        }*/
        System.out.println(System.currentTimeMillis() - statTime);
    }

    /**
     * 判断字符串是否为空
     * @param s
     */
    public static void testEqual_Lenth_IsEmpty(String s) {
        //效率极低，一个一个的去遍历字符
        if (null != s && !"".equals(s)) {

        }

        if (null != s && s.length() != 0) {

        }
        /**   这两种方法等价，效率比较好
         *    \\
         *    \\
         */
        if (null != s && !s.isEmpty()) {

        }
    }
}
