package com.sen.concurrency2.chapter5;

/**
 * @Author: Sen
 * @Date: 2019/12/9 18:50
 * @Description: 单线程执行设计模式，有一个门，只能有一个人通过。
 */
public class Client {

    public static void main(String[] args) {
        Gate gate = new Gate();
        User bj = new User(gate, "baobao", "beijing");
        User sz = new User(gate, "shenshen", "shenzhen");
        User gz = new User(gate, "guanglao", "guangzhou");
        bj.start();
        sz.start();
        gz.start();
    }
}
