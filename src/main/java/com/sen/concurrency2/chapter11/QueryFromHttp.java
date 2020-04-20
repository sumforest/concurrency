package com.sen.concurrency2.chapter11;

/**
 * @Author: Sen
 * @Date: 2019/12/10 18:34
 * @Description:
 */
public class QueryFromHttp {

    public void execute() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ContextAction.getContextAction().getContext()
                .setCardId(getCardId(ContextAction.getContextAction().getContext().getName()));
    }

    private String getCardId(String name) {
        return "32465134" + Thread.currentThread().getId();
    }

}
