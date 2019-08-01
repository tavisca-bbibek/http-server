package com.tavisca.workshops.second.httpserver.thread;

import com.tavisca.workshops.second.httpserver.HttpRequestParser;
import com.tavisca.workshops.second.httpserver.HttpServer;
import com.tavisca.workshops.second.httpserver.exception.HttpRequestParseException;
import com.tavisca.workshops.second.httpserver.exception.InvalidResourceFormatException;
import com.tavisca.workshops.second.httpserver.model.HttpRequest;
import com.tavisca.workshops.second.httpserver.util.FileHandler;
import com.tavisca.workshops.second.httpserver.HeaderGenerator;
import com.tavisca.workshops.second.httpserver.util.ResourceHandler;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ResponderThread implements Runnable {
    Socket client;

    public ResponderThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        InputStream requestStream = null;
        OutputStream responseStream = null;
        try {
            requestStream = client.getInputStream();
            responseStream = client.getOutputStream();
            String requestString = readRequest(requestStream);
            writeResponse(requestString, responseStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
               try{
                   requestStream.close();
                   responseStream.close();
               }catch (IOException e){
                   System.out.println(e.getMessage());
               }
        }
    }

    private String readRequest(InputStream requestStream) throws IOException {
        int size = requestStream.available();
        byte[] buffer = new byte[size];
        requestStream.readNBytes(buffer, 0, size);

        System.out.println(Thread.currentThread().getId() + "--------RequestString-------");
        System.out.write(buffer);

        return new String(buffer);
    }

    private void writeResponse(String requestString, OutputStream responseStream) throws IOException {
        byte[] response = null;
        try {
            HttpRequest request = HttpRequestParser.parse(requestString);
            switch (request.getType()) {
                case GET:
                    response = respondGetRequest(request);
                    responseStream.write(response);
                    responseStream.flush();
            }
        } catch (HttpRequestParseException e) {
            response = respondClientError();
            responseStream.write(response);
            responseStream.flush();
        } finally {
            System.out.println(Thread.currentThread().getId() + "---------Response---------");
            System.out.write(response);
            System.out.println(Thread.currentThread().getId() + "--------------------------");

            if (response != null)
                responseStream.close();
            System.out.println(Thread.currentThread().getId() + "---------Served---------");
        }
    }

    private byte[] respondGetRequest(HttpRequest request) {
        String resource = request.getResource();
        if (resource.equals("")) {
            request.setResource("index.html");
            return respondGetRequest(request);
        } else {
            try {
                try {
                    String mimeType = ResourceHandler.getMimeType(resource);
                    byte[] responseBody = FileHandler.readFile(resource);
                    String responseHeader = HeaderGenerator.generate(request.getProtocol(),
                            200, responseBody.length, mimeType);
                    return combineArrays(responseHeader.getBytes(), responseBody);
                } catch (InvalidResourceFormatException e) {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return respondFileNotFound(request);
            }
        }
    }

    private byte[] combineArrays(byte[] arr1, byte[] arr2) {
        byte[] combined = new byte[arr1.length + arr2.length];
        ByteBuffer buffer = ByteBuffer.wrap(combined);
        buffer.put(arr1)
                .put(arr2);
        return combined;
    }

    private byte[] respondFileNotFound(HttpRequest request) {
        byte[] responseBody;
        try {
            responseBody = FileHandler.readFile(HttpServer.responsesDirectory + "/" + HttpServer.responsesDirectory + "/" + "fileNotFound.html");
        } catch (FileNotFoundException e) {
            return respondServerError(request);
        }
        String responseHeader = HeaderGenerator.generate(request.getProtocol(), 200, responseBody.length, "text/html");
        return (responseHeader + responseBody).getBytes();
    }

    private byte[] respondServerError(HttpRequest request) {
        String responseBody = request.getResource() + " - Does Not Exist\n";
        String responseHeader = HeaderGenerator.generate(request.getProtocol(), 500, responseBody.length(), "plain/text");
        return (responseHeader + responseBody).getBytes();
    }

    private byte[] respondClientError() {
        String responseBody = "Bad Request - You ugly man.\n";
        String responseHeader = HeaderGenerator.generate("HTTP/1.1", 400, responseBody.length(), "text/plain");
        return (responseHeader + responseBody).getBytes();
    }
}
