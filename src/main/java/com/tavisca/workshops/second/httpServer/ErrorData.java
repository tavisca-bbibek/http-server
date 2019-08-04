package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.exception.InaccessibleFileException;
import com.tavisca.workshops.second.httpServer.util.FileHandler;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class ErrorData {
    private static final Logger logger = Logger.getLogger("com.tavisca.workshops.second.httpServer.ErrorData");

    private static final String MESSAGE_FILE_MISSING = "Server file missing";
    private static final String FILE_FILE_NOT_FOUND = "responses/fileNotFound.html";
    private static final String FILE_SERVER_ERROR = "responses/serverError.html";
    private static final String FILE_CLIENT_ERROR = "responses/clientError.html";

    static byte[] fileNotFound() {
        byte[] content;
        try {
            content = FileHandler.readFile(FILE_FILE_NOT_FOUND);
        } catch (FileNotFoundException e) {
            logger.severe(Thread.currentThread().getName() + "Cant find - " + FILE_FILE_NOT_FOUND);
            return serverError();
        }catch (InaccessibleFileException e) {
            logger.severe(Thread.currentThread().getName() + "Cant access - " + FILE_FILE_NOT_FOUND);
            return serverError();
        }
        return content;
    }

    static byte[] serverError() {
        byte[] content;
        try {
            content = FileHandler.readFile(FILE_SERVER_ERROR);
        } catch (FileNotFoundException e) {
            logger.severe(Thread.currentThread().getName() + "Cant find - " + FILE_SERVER_ERROR);
            throw new IllegalStateException(MESSAGE_FILE_MISSING, e);
        }catch (InaccessibleFileException e) {
            logger.severe(Thread.currentThread().getName() + "Cant access - " + FILE_SERVER_ERROR);
            return serverError();
        }
        return content;
    }

    public static byte[] clientError() {
        byte[] content;
        try {
            content = FileHandler.readFile(FILE_CLIENT_ERROR);
        } catch (FileNotFoundException e) {
            logger.severe(Thread.currentThread().getName() + "Cant find - " + FILE_CLIENT_ERROR);
            throw new IllegalStateException(MESSAGE_FILE_MISSING, e);
        } catch (InaccessibleFileException e) {
            logger.severe(Thread.currentThread().getName() + "Cant access - " + FILE_SERVER_ERROR);
            return serverError();
        }
        return content;
    }
}
