package com.sen.concurrency2.chapter5;

/**
 * @Author: Sen
 * @Date: 2019/12/9 18:43
 * @Description: 门
 */
public class Gate {

    private int count = 0;

    private String name = "Nobody";

    private String address = "Nowhere";

    public synchronized void pass(String name, String address) {
        this.count++;
        this.name = name;
        this.address = address;
        verify();
    }

    /**
     * 验证：是否可以通过门
     */
    private void verify() {
        // 若名字的第一个字母和地址的第一个字符相等则认为可以通过门
        if (name.charAt(0) != address.charAt(0)) {
            System.out.println("*******BLOCK********" + this.toString());
        } /*else {
            System.out.println("---------PASS-------" + toString());
        }*/
    }

    @Override
    public String toString() {
        return "NO." + count +
                ", name='" + name + '\'' +
                ", address='" + address ;
    }
}
