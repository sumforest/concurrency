package com.sen.concurrency.chapter2;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 19:09
 * @Description:
 */
public class Bank {

    public static void main(String[] args) {
        TicketWindow window = new TicketWindow("一号窗口");
        window.start();

        TicketWindow window2 = new TicketWindow("二号窗口");
        window2.start();

        TicketWindow window3 = new TicketWindow("三号窗口");
        window3.start();
    }
}
