package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.exception.InvalidResourceFormatException;
import com.tavisca.workshops.second.httpserver.model.Request;
import com.tavisca.workshops.second.httpserver.util.FileHandler;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

public class ResponseGenerator {

    public static byte[] generate(Request request) {
        String resourcePath = request.getResourcePath();
        if (resourcePath.isEmpty()) {
            request.setResourcePath(Server.FILE_DEFAULT);
            return generate(request);
        } else {
            try {
                try {
                    String mimeType = Server.getMimeType(resourcePath);
                    byte[] body = FileHandler.readFile(resourcePath);
                    String header = ResponseHeaderGenerator.generate(request.getProtocol(),
                            200, body.length, mimeType);
                    return combineArrays(header.getBytes(), body);
                } catch (InvalidResourceFormatException e) {
                    return Response.clientError();
                }
            } catch (FileNotFoundException e) {
                return Response.fileNotFound();
            }
        }
    }

    public static byte[] combineArrays(byte[] arr1, byte[] arr2) {
        byte[] combined = new byte[arr1.length + arr2.length];
        ByteBuffer buffer = ByteBuffer.wrap(combined);
        buffer.put(arr1)
                .put(arr2);
        return combined;
    }
}
