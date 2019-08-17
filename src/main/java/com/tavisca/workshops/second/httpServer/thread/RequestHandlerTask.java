package com.tavisca.workshops.second.httpServer.thread;

import com.tavisca.workshops.second.httpServer.RequestParser;
import com.tavisca.workshops.second.httpServer.ErrorData;
import com.tavisca.workshops.second.httpServer.Response;
import com.tavisca.workshops.second.httpServer.exception.RequestParseException;
import com.tavisca.workshops.second.httpServer.Header;
import com.tavisca.workshops.second.httpServer.Request;
import com.tavisca.workshops.second.httpServer.model.RequestMethod;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class RequestHandlerTask implements Runnable {
    private static final int BUFFER_SIZE = 128;
    private final Logger logger = Logger.getLogger("com.tavisca.workshops.second.httpServer.RequestHandlerTask");
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
        char[] requestData = new char[size];
        InputStreamReader requestStreamReader = new InputStreamReader(requestStream);
        requestStreamReader.read(requestData, 0, size);

        if (requestData.length == 0)
            logger.severe(Thread.currentThread().getName() + " - Buffer is null");

        return new String(requestData);
    }

    private void writeResponse(String requestString, OutputStream responseStream) throws IOException {

        try {
            Request request = RequestParser.parse(requestString);
            if (request.getMethod() == RequestMethod.GET) {
                Response response = new Response(request);
                responseStream.write(response.getBytes());
                responseStream.flush();
            }
        } catch (RequestParseException e) {
            byte[] body = ErrorData.clientError();
            Header header = new Header(body.length).clientError();
            Response response = new Response(header, body);
            responseStream.write(response.getBytes());
            responseStream.flush();
            logger.warning(Thread.currentThread().getName() + " - couldn't understand request - "
                    + requestString + " - response header -"
                    + header.toString());
        } finally {
            responseStream.close();
        }
    }
}
