package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.thread.ResponderThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final int PORT = 80;
    public static final String rootDirectory = "www";
    public static final String responsesDirectory = "Responses";
    public static final Map<Integer, String> statusCodeToStringMap = Collections.unmodifiableMap(
            new HashMap<Integer, String>(){{
                put(200, "OK");
                put(400, "Bad Request");
                put(404, "Not Found");
                put(500, "Internal Server Error");
            }}
    );

    public static final int THREAD_POOL_SIZE = 10;
    public ServerSocket server = null;
    private static HttpServer instance = null;

    public HttpServer() {
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
                Socket socket = HttpServer.getInstance().acceptRequest();
                System.out.println("Connected to:" + socket.getInetAddress());
                threadPool.submit(new ResponderThread(socket));
//                new ResponderThread(socket).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static HttpServer getInstance() {
        if (instance == null)
            instance = new HttpServer();
        return instance;
    }

    public Socket acceptRequest() throws IOException {
        System.out.println("Waiting for client to connect.");
        return server.accept();
    }
}
