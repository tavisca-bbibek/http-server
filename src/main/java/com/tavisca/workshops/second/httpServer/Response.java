package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.exception.InaccessibleFileException;
import com.tavisca.workshops.second.httpServer.exception.InvalidResourceFormatException;
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
        if (request.getResourcePath().isEmpty()) {
            request.setResourcePath(request.getResourcePath() + "/" + FILE_DEFAULT);
        } else if (!request.getResourcePath().matches(PATTERN_FILE_PATH)) {
            request.setResourcePath(request.getResourcePath() + "/" + FILE_DEFAULT);
        }
        initialize();
    }

    public Response(byte[] header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    private void initialize() {
        try {
            try {
                body = FileHandler.readFile(request.getResourcePath());
                String h = new Header(body.length, request.getMimeType()).toString();
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
