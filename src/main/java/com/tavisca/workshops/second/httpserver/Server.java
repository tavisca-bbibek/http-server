package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.exception.InvalidResourceFormatException;
import com.tavisca.workshops.second.httpserver.thread.RequestHandlerTask;
import com.tavisca.workshops.second.httpserver.util.ResourcePathParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 80;
    public static final String DIRECTORY_ROOT = "www";
    public static final String DIRECTORY_LOG = "logs";
    static final String FILE_DEFAULT = "index.html";

    private static final int THREAD_POOL_SIZE = 100;

    public static final Map<Integer, String> statusCodeToStringMap = Map.of(
            200, "OK",
            400, "Bad Request",
            404, "Not Found",
            500, "Internal Server Error");

    private static final Map<String, String> extensionToMimeMap;

    static {
        extensionToMimeMap = new HashMap<String, String>() {{
            put("html", "text/html");
            put("htm", "text/html");
            put("js", "text/text/javascript");
            put("css", "text/css");
            put("jpeg", "image/jpeg");
            put("jpg", "image/jpeg");
            put("png", "image/png");
            put("ico", "image/x-icon");
            put("txt", "plain/text");
            put("otf", "font/otf");
            put("ttf", "font/ttf");
            put("svg", "image/svg+xml");
            put("eot", "application/vnd.ms-fontobject");
            put("woff", "font/woff");
            put("woff2", "font/woff2");
        }};
    }

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

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        while (true) {
            try {
                Socket socket = Server.getInstance().acceptRequest();
                //TODO: Log request client.
                threadPool.submit(new RequestHandlerTask(socket));
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
        return server.accept();
    }

    public static String getMimeType(String resource) throws InvalidResourceFormatException {
        return extensionToMimeMap.get(ResourcePathParser.parseExtension(resource));
    }
}
