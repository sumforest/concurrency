package com.sen.concurrency2.chapter5;

/**
 * @Auther: Sen
 * @Date: 2019/12/9 18:43
 * @Description:
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

    private void verify() {
        if (name.charAt(0) != address.charAt(0)) {
            System.out.println("*******BLOCK********" + toString());
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
