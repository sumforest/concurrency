package com.sen.concurrency1.chapter5;

/**
 * @Author: Sen
 * @Date: 2019/12/7 02:41
 * @Description: 使用 {@code join()} 模拟实现多线程采集集器信息
 */
public class ThreadJoinDemo {

    public static void main(String[] args) throws InterruptedException {
        long startTimeStamp = System.currentTimeMillis();
        Thread t1 = new Thread(new Machine("机器1", 5_000));
        Thread t2 = new Thread(new Machine("机器2", 7_000));
        Thread t3 = new Thread(new Machine("机器3", 3_000));

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        long endTimeStamp = System.currentTimeMillis();
        System.out.printf("%s采集数据开始,%s结束，现在开始保存\n",startTimeStamp,endTimeStamp);
    }
}

class Machine implements Runnable{

    private String name;

    private long spendTime;

    public Machine(String name, long spendTime) {
        this.name = name;
        this.spendTime = spendTime;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(spendTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s,completed capture at [%s]\n", name, System.currentTimeMillis());
    }
}
