package com.sen.concurrency1.chapter10;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * @Author: Sen
 * @Date: 2019/12/8 01:22
 * @Description:
 */
public class BooleanLock implements Lock{

    /**
     * true表示锁已经被占有，false表示锁空闲可以争夺
     */
    private volatile boolean initValue;

    private final Collection<Thread> blockThreads = new ArrayList<>();

    /**
     * 当前持有锁的线程，防止非持有锁的线程释放锁
     */
    private Thread currentThread;

    public BooleanLock() {
        this.initValue = false;
    }


    @Override
    public synchronized void lock() throws InterruptedException {
        //锁已经被占用
        while (initValue){
            Optional.of(Thread.currentThread().getName() + " is blocked").ifPresent(System.out::println);
            blockThreads.add(Thread.currentThread());
            this.wait();
        }
        Optional.of(Thread.currentThread().getName() + " get the monitor").ifPresent(System.out::println);
        //获取锁成功，从阻塞队列删除当前线程
        blockThreads.remove(Thread.currentThread());
        //设置持有锁的当前线程
        currentThread = Thread.currentThread();
        initValue = true;
    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, WaitLockTimeOutException {
        long judge = mills;
        long endTime = System.currentTimeMillis() + mills;
        while (initValue){
            if (judge <= 0) {
                throw new WaitLockTimeOutException("waiting lock time out");
            }
            Optional.of(Thread.currentThread().getName() + " is blocked").ifPresent(System.out::println);
            blockThreads.add(Thread.currentThread());
            this.wait(mills);
            //判断是否超时，期待结束时间 - 当前时间
            judge = endTime - System.currentTimeMillis();
        }
        //获取锁
        Optional.of(Thread.currentThread().getName() + " get the monitor").ifPresent(System.out::println);
        blockThreads.remove(Thread.currentThread());
        currentThread = Thread.currentThread();
        initValue = true;
    }

    @Override
    public synchronized void unlock() {
        //持有锁的线程才能释放锁
        if (currentThread == Thread.currentThread()) {
            Optional.of(Thread.currentThread().getName() + " release the monitor").ifPresent(System.out::println);
            initValue = false;
            //唤醒因抢锁阻塞的所有所有线程
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockThreads() {
        // 返回一个不可修改的集合
        return Collections.unmodifiableCollection(blockThreads);
    }

    @Override
    public int getBlockThreadsCount() {
        return blockThreads.size();
    }
}
