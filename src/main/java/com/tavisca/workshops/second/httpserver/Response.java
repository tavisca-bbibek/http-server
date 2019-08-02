package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.exception.InaccessibleFileException;
import com.tavisca.workshops.second.httpserver.util.FileHandler;

import java.io.FileNotFoundException;

public class Response {

    private static final String PROTOCOL = "HTTP/1.1";
    private static final String MIME_TYPE = "text/html";
    private static final String MESSAGE_FILE_MISSING = "Server file missing";
    private static final String FILE_FILE_NOT_FOUND = "responses/fileNotFound.html";
    private static final String FILE_SERVER_ERROR = "responses/serverError.html";
    private static final String FILE_CLIENT_ERROR = "responses/clientError.html";

    static byte[] fileNotFound() {
        byte[] body;
        try {
            body = FileHandler.readFile(FILE_FILE_NOT_FOUND);
        } catch (FileNotFoundException e) {
            return serverError();
        }catch (InaccessibleFileException e) {
            //TODO: log can't access server file
            return Response.serverError();
        }
        String header = ResponseHeaderGenerator.generate(PROTOCOL, 200, body.length, MIME_TYPE);
        return ResponseGenerator.combineArrays(header.getBytes(), body);
    }

    public static byte[] serverError() {
        byte[] body;
        try {
            body = FileHandler.readFile(FILE_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(MESSAGE_FILE_MISSING, e);
        }catch (InaccessibleFileException e) {
            //TODO: log can't access server file
            return Response.serverError();
        }
        String header = ResponseHeaderGenerator.generate(PROTOCOL, 500, body.length, MIME_TYPE);
        return ResponseGenerator.combineArrays(header.getBytes(), body);
    }

    public static byte[] clientError() {
        byte[] body;
        try {
            body = FileHandler.readFile(FILE_CLIENT_ERROR);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(MESSAGE_FILE_MISSING, e);
        } catch (InaccessibleFileException e) {
            //TODO: log can't access server file
            return Response.serverError();
        }
        String header = ResponseHeaderGenerator.generate(PROTOCOL, 400, body.length, "text/html");
        return ResponseGenerator.combineArrays(header.getBytes(), body);
    }
}
