package com.tavisca.workshops.second.httpServer.thread;

import com.tavisca.workshops.second.httpServer.RequestParser;
import com.tavisca.workshops.second.httpServer.Response;
import com.tavisca.workshops.second.httpServer.ResponseGenerator;
import com.tavisca.workshops.second.httpServer.exception.RequestParseException;
import com.tavisca.workshops.second.httpServer.model.Request;
import com.tavisca.workshops.second.httpServer.model.RequestMethod;

import java.io.*;
import java.net.Socket;

public class RequestHandlerTask implements Runnable {
    Socket client;

    public RequestHandlerTask(Socket client) {
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
            try {
                requestStream.close();
                responseStream.close();
                client.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String readRequest(InputStream requestStream) throws IOException {
        int size = requestStream.available();
        byte[] buffer = new byte[size];
        requestStream.readNBytes(buffer, 0, size);
        //TODO:log when buffer is null.
        return new String(buffer);
    }

    private void writeResponse(String requestString, OutputStream responseStream) throws IOException {
        byte[] response;
        try {
            Request request = RequestParser.parse(requestString);
            if (request.getMethod() == RequestMethod.GET) {
                response = ResponseGenerator.generate(request);
                responseStream.write(response);
                responseStream.flush();
            }
        } catch (RequestParseException e) {
            response = Response.clientError();
            responseStream.write(response);
            responseStream.flush();
            //TODO: Log client error.
        } finally {
            responseStream.close();
        }
    }
}
