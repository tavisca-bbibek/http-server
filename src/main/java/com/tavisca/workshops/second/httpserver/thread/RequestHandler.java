package com.tavisca.workshops.second.httpserver.thread;

import com.tavisca.workshops.second.httpserver.RequestParser;
import com.tavisca.workshops.second.httpserver.ResponseGenerator;
import com.tavisca.workshops.second.httpserver.exception.HttpRequestParseException;
import com.tavisca.workshops.second.httpserver.model.HttpRequest;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    Socket client;

    public RequestHandler(Socket client) {
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
            HttpRequest request = RequestParser.parse(requestString);
            switch (request.getType()) {
                case GET:
                    response = ResponseGenerator.generate(request);
                    responseStream.write(response);
                    responseStream.flush();
            }
        } catch (HttpRequestParseException e) {
            response = ResponseGenerator.generateClientError();
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
}
