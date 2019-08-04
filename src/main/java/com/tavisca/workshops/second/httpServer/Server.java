package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.thread.RequestHandlerTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

public class Server {
    private static final int PORT = 80;
    private static final int THREAD_POOL_SIZE = 100;
    private static Logger logger = Logger.getLogger("com.tavisca.workshops.second.httpServer");

    static {
        final String PATTERN_LOG_FILE = "logs/server_log_%g.txt";
        try {
            Handler handler = new FileHandler(PATTERN_LOG_FILE, 1000, 2, true);
            handler.setLevel(Level.ALL);
            handler.setFormatter(new SimpleFormatter());

            logger.addHandler(handler);
            logger.setLevel(Level.FINE);
        } catch (IOException e) {
            throw new RuntimeException("Can't write logs.");
        }
    }

    private ServerSocket server = null;
    private static Server instance = null;

    private Server() {
        try {
            server = new ServerSocket(PORT);
            logger.fine("Server started");
        } catch (IOException e) {
            logger.severe("Can't start server - " + e.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        while (true) {
            try {
                Socket socket = Server.getInstance().acceptRequest();
                logger.fine("Request from: " + socket.getInetAddress());
                threadPool.submit(new RequestHandlerTask(socket));
            } catch (Exception e) {
                logger.severe("Server crashed - " + e.toString());
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
