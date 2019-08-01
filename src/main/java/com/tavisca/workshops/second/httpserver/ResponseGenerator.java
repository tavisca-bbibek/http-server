package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.exception.InvalidResourceFormatException;
import com.tavisca.workshops.second.httpserver.model.HttpRequest;
import com.tavisca.workshops.second.httpserver.util.FileHandler;
import com.tavisca.workshops.second.httpserver.util.ResourceHandler;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

public class ResponseGenerator {

    public static byte[] generate(HttpRequest request) {
        String resource = request.getResource();
        if (resource.equals("")) {
            request.setResource("index.html");
            return generate(request);
        } else {
            try {
                try {
                    String mimeType = ResourceHandler.getMimeType(resource);
                    byte[] responseBody = FileHandler.readFile(resource);
                    String responseHeader = ResponseHeaderGenerator.generate(request.getProtocol(),
                            200, responseBody.length, mimeType);
                    return combineArrays(responseHeader.getBytes(), responseBody);
                } catch (InvalidResourceFormatException e) {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return generateFileNotFound(request);
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

    public static byte[] generateFileNotFound(HttpRequest request) {
        byte[] responseBody;
        try {
            responseBody = FileHandler.readFile(Server.responsesDirectory + "/" + Server.responsesDirectory + "/" + "fileNotFound.html");
        } catch (FileNotFoundException e) {
            return generateServerError(request);
        }
        String responseHeader = ResponseHeaderGenerator.generate(request.getProtocol(), 200, responseBody.length, "text/html");
        return (responseHeader + responseBody).getBytes();
    }

    public static byte[] generateServerError(HttpRequest request) {
        String responseBody = request.getResource() + " - Does Not Exist\n";
        String responseHeader = ResponseHeaderGenerator.generate(request.getProtocol(), 500, responseBody.length(), "plain/text");
        return (responseHeader + responseBody).getBytes();
    }

    public static byte[] generateClientError() {
        String responseBody = "Bad Request - You ugly man.\n";
        String responseHeader = ResponseHeaderGenerator.generate("HTTP/1.1", 400, responseBody.length(), "text/plain");
        return (responseHeader + responseBody).getBytes();
    }
}
