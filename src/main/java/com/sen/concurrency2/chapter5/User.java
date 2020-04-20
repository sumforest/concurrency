package com.sen.concurrency2.chapter5;

/**
 * @Author: Sen
 * @Date: 2019/12/9 18:48
 * @Description:
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
        while (true) {
            gate.pass(username,userAddress);
        }
    }
}
