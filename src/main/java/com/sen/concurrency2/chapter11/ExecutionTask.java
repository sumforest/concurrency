package com.sen.concurrency2.chapter11;

/**
 * @Auther: Sen
 * @Date: 2019/12/10 18:31
 * @Description:
 */
public class ExecutionTask implements Runnable {

    private final QueryFromDB queryFromDB = new QueryFromDB();

    private final QueryFromHttp queryFromHttp = new QueryFromHttp();

    @Override
    public void run() {
        queryFromDB.execute();
        System.out.println("query name is successful");
        queryFromHttp.execute();
        System.out.println("query cardId is successful");
        Context context = ContextAction.getContextAction().getContext();
        System.out.println(Thread.currentThread().getName() + " context:-->" + context.getName() + "-->" +context.getCardId());
    }
}
