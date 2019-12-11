package com.sen.concurrency2.chapter18;

/**
 * @Auther: Sen
 * @Date: 2019/12/11 17:35
 * @Description:
 */
public class DisplayStringRequestClient extends Thread {
    private final ActiveObject activeObject;

    public DisplayStringRequestClient(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                String text = Thread.currentThread().getName() + "=>" + i;
                activeObject.displayString(text);
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
