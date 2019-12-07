package com.sen.concurrency.chapter6;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 15:56
 * @Description:
 */
public class ThreadCloseForce {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ThreadService threadService = new ThreadService();
        threadService.execute(()->{
            while (true){

            }
        });
        threadService.shutdown(5000);
        long end =System.currentTimeMillis();
        System.out.println(end - start);
    }
}
