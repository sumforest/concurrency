package com.sen.concurrency2.chapter6;

/**
 * @Auther: Sen
 * @Date: 2019/12/9 20:16
 * @Description: 共享资源
 */
public class SharedData {

    private final ReadWriteLock LOCK = new ReadWriteLock();

    private char[] data;

    public SharedData(int size) {
        data = new char[size];
        for (int i = 0; i < size; i++) {
            data[i] = '*';
        }
    }

    public void wirteData(char ch) {
        try {
            LOCK.writeLock();
            for (int i = 0; i < data.length; i++) {
                data[i] = ch;
                tosleep(10);
            }
        } finally {
            LOCK.writeUnlock();
        }
    }

    public char[] readData() {
       try {
           LOCK.readLock();
           char[] target = new char[data.length];
           for (int i = 0; i < data.length; i++) {
               target[i] = data[i];
           }
           return target;
       }finally {
           LOCK.readUnlock();
       }
    }

    private void tosleep(int i) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

