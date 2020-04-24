package com.sen.concurrency2.chapter5;

/**
 * @Author: Sen
 * @Date: 2019/12/9 18:48
 * @Description: 用户，每一个用户用一个线程来表示
 */
public class User extends Thread {

    private Gate gate;

    private String username;

    private String userAddress;

    public User(Gate gate, String username, String userAddress) {
        this.userAddress = userAddress;
        this.username = username;
        this.gate = gate;
    }

    @Override
    public void run() {
        System.out.println(username + "BEGIN");
        // 用户通过门
        while (true) {
            gate.pass(username,userAddress);
        }
    }
}
