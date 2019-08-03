package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.exception.InaccessibleFileException;
import com.tavisca.workshops.second.httpServer.exception.InvalidResourceFormatException;
import com.tavisca.workshops.second.httpServer.model.Request;
import com.tavisca.workshops.second.httpServer.util.FileHandler;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

public class ResponseGenerator {

    private static final String PATTERN_FILE_PATH = "(.*)\\.(.*)$";

    public static byte[] generate(Request request) {
        String resourcePath = request.getResourcePath();
        if (resourcePath.isEmpty()) {
            request.setResourcePath(Server.FILE_DEFAULT);
            return generate(request);
        } else if(!resourcePath.matches(PATTERN_FILE_PATH)) {
            request.setResourcePath(resourcePath + "/" + Server.FILE_DEFAULT);
            return generate(request);
        }
        try {
            try {
                String mimeType = Server.getMimeType(resourcePath);
                byte[] body = FileHandler.readFile(resourcePath);
                byte[] header = ResponseHeaderGenerator.generate(request.getProtocol(),
                        200, body.length, mimeType);
                return combineArrays(header, body);
            } catch (InvalidResourceFormatException e) {
                byte[] body = Response.clientError();
                byte[] header = ResponseHeaderGenerator.generateClientError(body.length);
                return combineArrays(header, body);
            }
        } catch (FileNotFoundException e) {
            byte[] body = Response.fileNotFound();
            byte[] header = ResponseHeaderGenerator.generateFileNotFound(body.length);
            return combineArrays(header, body);
        } catch (InaccessibleFileException e) {
            byte[] body = Response.fileNotFound();
            byte[] header = ResponseHeaderGenerator.generateServerError(body.length);
            return combineArrays(header, body);
        }
    }

    private static byte[] combineArrays(byte[] arr1, byte[] arr2) {
        byte[] response = new byte[arr1.length + arr2.length];
        ByteBuffer buffer = ByteBuffer.wrap(response);
        buffer.put(arr1)
                .put(arr2);
        return response;
    }
}
