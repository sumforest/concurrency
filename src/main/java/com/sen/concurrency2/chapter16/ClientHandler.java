package com.sen.concurrency2.chapter16;

import java.io.*;
import java.net.Socket;

/**
 * @Auther: Sen
 * @Date: 2019/12/11 00:41
 * @Description:
 */
public class ClientHandler implements Runnable {

    private final Socket client;

    private volatile boolean running = true;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try (InputStream inputStream = client.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
             OutputStream outputStream = client.getOutputStream();
             PrintWriter writer = new PrintWriter(outputStream)) {
            while (running) {
                String message = br.readLine();
                System.out.println("receive message: " + message + " from client");
                writer.print("response: " + message);
                writer.flush();
            }
        } catch (IOException e) {
            // e.printStackTrace();
            running = false;
        } finally {
            stop();
        }
    }

    public void stop() {
        if (!running)
            return;
        this.running = false;
        try {
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
