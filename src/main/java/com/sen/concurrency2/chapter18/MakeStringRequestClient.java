package com.sen.concurrency2.chapter18;

/**
 * @Auther: Sen
 * @Date: 2019/12/11 17:38
 * @Description:
 */
public class MakeStringRequestClient extends Thread {

    private final ActiveObject activeObject;

    private char fillchar;

    public MakeStringRequestClient(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        this.fillchar = name.charAt(0);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Result result = activeObject.makeString(i, fillchar);
                Thread.sleep(200);
                System.out.println(Thread.currentThread().getName() + ": value = " + result.getResultValue());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
