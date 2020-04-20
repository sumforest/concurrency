package com.sen.concurrency2.chapter18;

/**
 * @Author: Sen
 * @Date: 2019/12/11 16:22
 * @Description:
 */
class Servant implements ActiveObject {

    @Override
    public Result makeString(int count, char fillchar) {
        char[] buf = new char[count];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = fillchar;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new RealResult(new String(buf));
    }

    @Override
    public void displayString(String text) {
        System.out.println("display:" + text);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
