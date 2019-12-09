package com.sen.concurrency2.chapter7;

/**
 * @Auther: Sen
 * @Date: 2019/12/9 21:42
 * @Description:
 */
final public class Person {

    private final String name;

    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
