package com.sen.concurrency2.chapter16;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Sen
 * @Date: 2019/12/11 00:03
 * @Description:
 */
public class AppServer extends Thread {

    private int port;

    private final static int DEFAULT_PORT = 8090;

    private volatile boolean start = true;

    private List<ClientHandler> clientHandlers = new ArrayList<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private ServerSocket serverSocket;

    public AppServer(){
        this(DEFAULT_PORT);
    }

    public AppServer(int prot) {
        this.port = prot;
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(port);
            while (start) {
                Socket client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                executor.submit(clientHandler);
                clientHandlers.add(clientHandler);
            }
        } catch (IOException e) {
            // throw new RuntimeException(e);
        }finally {
            this.dispose();
        }
    }

    private void dispose() {
        clientHandlers.forEach(ClientHandler::stop);
        executor.shutdown();
    }

    public void shutdown() throws IOException {
        this.start = false;
        this.serverSocket.close();
        this.interrupt();
    }
}
