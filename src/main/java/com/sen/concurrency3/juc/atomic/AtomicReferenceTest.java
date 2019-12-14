package com.sen.concurrency3.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Auther: Sen
 * @Date: 2019/12/14 23:52
 * @Description:
 */
public class AtomicReferenceTest {


    public static void main(String[] args) {
        AtomicReference<Simple> reference = new AtomicReference<>(new Simple("12306", "铁道部"));
        Simple simple = reference.get();
        System.out.println(simple.getName());
        boolean success = reference.compareAndSet(simple, new Simple("铁道部", "铁道部"));
        System.out.println(success);
        System.out.println(reference.get().getName());
    }

    private static class Simple{
        private String name;

        private String age;

        public Simple(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
