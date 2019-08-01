package com.tavisca.workshops.second.httpserver.thread;

import com.tavisca.workshops.second.httpserver.HttpRequestParser;
import com.tavisca.workshops.second.httpserver.HttpServer;
import com.tavisca.workshops.second.httpserver.exception.HttpRequestParseException;
import com.tavisca.workshops.second.httpserver.model.HttpRequest;
import com.tavisca.workshops.second.httpserver.util.FileHandler;
import com.tavisca.workshops.second.httpserver.HeaderGenerator;

import java.io.*;
import java.net.Socket;

public class ResponderThread implements Runnable {
    String requestString;
    PrintStream responseStream;

    public ResponderThread(Socket client) {
        try {
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuilder requestStringBuilder = new StringBuilder();
            String line;
            while (!(line = clientReader.readLine()).isEmpty())
                requestStringBuilder.append(line)
                        .append('\n');

            requestString = requestStringBuilder.toString();
            System.out.println("--------RequestString-------");
            System.out.println(requestStringBuilder.toString());
            System.out.println("----------------------------");

            responseStream = new PrintStream(client.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
//        testResponse();

        HttpRequest request;
        String response = "";
        try {
            request = HttpRequestParser.parse(requestString);
            switch (request.getType()) {
                case GET:
                    response = respondGetRequest(request);
            }
        } catch (HttpRequestParseException e) {
            response = respondClientError();
        }

        responseStream.print(response);
        System.out.println("---------Response---------");
        System.out.println(response);
        System.out.println("--------------------------");

        responseStream.flush();
        responseStream.close();
        System.out.println("---------Served---------");
    }

    private String respondGetRequest(HttpRequest request) {
        String resource = request.getResource();
        if (resource.equals("")) {
            request.setResource("index.html");
            return respondGetRequest(request);
        } else {
            try {
                String responseBody = FileHandler.readFile(resource);
                String responseHeader = HeaderGenerator.generate(request.getProtocol(), 200, responseBody.length(), "text/html");
                return responseHeader + responseBody;
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return respondFileNotFound(request);
            }
        }
    }

    private String respondFileNotFound(HttpRequest request) {
        String responseBody = null;
        try {
            responseBody = FileHandler.readFile(HttpServer.responsesDirectory + "/" + HttpServer.responsesDirectory + "/" + "fileNotFound.html");
        } catch (FileNotFoundException e) {
           return respondServerError(request);
        }
        String responseHeader = HeaderGenerator.generate(request.getProtocol(), 200, responseBody.length(), "text/html");
        return  responseHeader + responseBody;
    }

    private String respondServerError(HttpRequest request) {
        String responseBody = request.getResource() + " - Does Not Exist";
        String responseHeader = HeaderGenerator.generate(request.getProtocol(), 500, responseBody.length(), "plain/text");
        return responseHeader + responseBody;
    }

    private String respondClientError() {
        //TODO: respond clientError
        return null;
    }

   /* private void testResponse() {
        //Response header
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("HTTP/1.1 200 OK\n")
                .append("Date: Mon, 27 Jul 2009 12:28:53 GMT\n")
                .append("Server: Custom-Server/1.0\n");

        //Response body
        byte[] responseBody;
        try {
            responseBody = FileHandler.readFile("index.html");
        } catch (FileNotFoundException e) {
            try {
                responseBody = FileHandler.readFile(HttpServer.responsesDirectory + "/" + "fileNotFound.html");
            } catch (FileNotFoundException ex) {
                responseBody = "File Not Found".getBytes();
            }
        }
        headerBuilder.append("Content-Length: " + responseBody.length + '\n')
                .append("Connection: Closed\n")
                .append("Content-Type: text/html\n");

        System.out.println("--------Response-------");
        System.out.println(headerBuilder.toString());
        System.out.println(new String(responseBody));
        System.out.println("----------------------------");

        responseStream.print(new String(responseBody));
    }*/
}
