package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.thread.RequestHandlerTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 80;
    public static final String DIRECTORY_ROOT = "www";
    public static final String DIRECTORY_LOG = "logs";
    static final String FILE_DEFAULT = "index.html";

    private static final int THREAD_POOL_SIZE = 100;

    private ServerSocket server = null;
    private static Server instance = null;

    private Server() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started...");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        while (true) {
            try {
                Socket socket = Server.getInstance().acceptRequest();
                threadPool.submit(new RequestHandlerTask(socket));
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private static Server getInstance() {
        if (instance == null)
            instance = new Server();
        return instance;
    }

    private Socket acceptRequest() throws IOException {
        return server.accept();
    }
}
