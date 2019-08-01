package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.thread.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 80;
    public static final String rootDirectory = "www";
    public static final String responsesDirectory = "Responses";
    public static final Map<Integer, String> statusCodeToStringMap = Map.of(
            200, "OK",
            400, "Bad Request",
            404, "Not Found",
            500, "Internal Server Error");

    public static final int THREAD_POOL_SIZE = 10;
    public ServerSocket server = null;
    private static Server instance = null;

    public Server() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started...");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        while(true){
            try {
                Socket socket = Server.getInstance().acceptRequest();
                System.out.println("Connected to:" + socket.getInetAddress());
                threadPool.submit(new RequestHandler(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Server getInstance() {
        if (instance == null)
            instance = new Server();
        return instance;
    }

    public Socket acceptRequest() throws IOException {
        System.out.println("Waiting for client to connect.");
        return server.accept();
    }
}
