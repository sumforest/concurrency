package com.sen.concurrency2.chapter7;

import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/9 21:44
 * @Description: 不可变对象
 */
public class ImmutableClient {

    public static void main(String[] args) {
        Person person = new Person("Tom", 23);
        IntStream.range(0, 5).forEach(i ->new UsePerson(person).start());
    }
}
