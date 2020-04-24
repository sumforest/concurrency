package com.sen.concurrency2.chapter7;

/**
 * @Author: Sen
 * @Date: 2019/12/9 21:43
 * @Description:
 */
public class UsePerson extends Thread{

    private final Person person;

    public UsePerson(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "-->" + person.toString());
        }
    }
}
