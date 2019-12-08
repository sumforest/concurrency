package com.sen.concurrency1.chapter8;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 19:02
 * @Description:
 */
public class DeadLock {

    private final Object LOCK = new Object();

    private OtherService otherService;

    public DeadLock(OtherService otherService) {
        this.otherService = otherService;
    }

    public void m1() {
        synchronized (LOCK) {
            System.out.println("m1========");
            otherService.s1();
        }
    }

    public void m2(){
        synchronized (LOCK) {
            System.out.println("m2======");
        }
    }
}
