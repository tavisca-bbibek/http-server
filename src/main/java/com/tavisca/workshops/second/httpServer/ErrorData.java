package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.exception.InaccessibleFileException;
import com.tavisca.workshops.second.httpServer.util.FileHandler;

import java.io.FileNotFoundException;

public class ErrorData {
    private static final String MESSAGE_FILE_MISSING = "Server file missing";
    private static final String FILE_FILE_NOT_FOUND = "responses/fileNotFound.html";
    private static final String FILE_SERVER_ERROR = "responses/serverError.html";
    private static final String FILE_CLIENT_ERROR = "responses/clientError.html";

    public static byte[] fileNotFound() {
        byte[] content;
        try {
            content = FileHandler.readFile(FILE_FILE_NOT_FOUND);
        } catch (FileNotFoundException e) {
            return serverError();
        }catch (InaccessibleFileException e) {
            //TODO: log can't access server file
            return serverError();
        }
        return content;
    }

    private static byte[] serverError() {
        byte[] content;
        try {
            content = FileHandler.readFile(FILE_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(MESSAGE_FILE_MISSING, e);
        }catch (InaccessibleFileException e) {
            //TODO: log can't access server file
            return serverError();
        }
        return content;
    }

    public static byte[] clientError() {
        byte[] content;
        try {
            content = FileHandler.readFile(FILE_CLIENT_ERROR);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(MESSAGE_FILE_MISSING, e);
        } catch (InaccessibleFileException e) {
            //TODO: log can't access server file
            return serverError();
        }
        return content;
    }
}
