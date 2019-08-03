package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.exception.InaccessibleFileException;
import com.tavisca.workshops.second.httpServer.exception.InvalidResourceFormatException;
import com.tavisca.workshops.second.httpServer.model.Header;
import com.tavisca.workshops.second.httpServer.model.Request;
import com.tavisca.workshops.second.httpServer.util.FileHandler;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

public class Response {

    private static final String PATTERN_FILE_PATH = "(.*)\\.(.*)$";
    private static final String FILE_DEFAULT = "index.html";
    private Request request;
    private byte[] header;
    private byte[] body;

    public Response(Request request) {
        this.request = request;
        initialize();
    }

    public Response(byte[] header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    private void initialize() {
        String resourcePath = request.getResourcePath();
        if (resourcePath.isEmpty()) {
            request.setResourcePath(FILE_DEFAULT);
            initialize();
        } else if (!resourcePath.matches(PATTERN_FILE_PATH)) {
            request.setResourcePath(resourcePath + "/" + FILE_DEFAULT);
            initialize();
        }
        try {
            try {
                body = FileHandler.readFile(resourcePath);
                header = new Header(body.length, request.getMimeType()).getBytes();
            } catch (InvalidResourceFormatException e) {
                body = ErrorData.clientError();
                header = new Header(body.length).clientError();
            }
        } catch (FileNotFoundException e) {
            body = ErrorData.fileNotFound();
            header = new Header(body.length).fileNotFound();
        } catch (InaccessibleFileException e) {
            body = ErrorData.fileNotFound();
            header = new Header(body.length).serverError();
        }
    }

    public byte[] getBytes() {
        byte[] response = new byte[header.length + body.length];
        ByteBuffer buffer = ByteBuffer.wrap(response);
        buffer.put(header)
                .put(body);
        return response;
    }
}
