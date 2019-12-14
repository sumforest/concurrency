package com.sen.concurrency3.juc.atomic;

/**
 * @Auther: Sen
 * @Date: 2019/12/14 22:15
 * @Description:
 */
public class GetLockFailedExeption extends Exception {

    public GetLockFailedExeption() {

    }

    public GetLockFailedExeption(String message) {
        super(message);
    }
}
