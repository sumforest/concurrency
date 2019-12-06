package com.sen.concurrency.chapter3;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 23:13
 * @Description: 测试Thread构造方法参数stackSize堆虚拟机栈大小的影响
 */
public class CreateThread3 {

    static int count = 0;

    public static void main(String[] args) {
        Thread thread = new Thread(null, new Runnable() {
            @Override
            public void run() {
                try {
                    add(1);
                } catch (Error e) {
                    e.printStackTrace();
                    System.out.println(count);
                }
            }

            private void add(int i){
                ++count;
                add(i + 1);
            }
        },"test",1 << 24);
        thread.start();
    }
}
