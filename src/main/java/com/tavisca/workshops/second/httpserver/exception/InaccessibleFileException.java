package com.tavisca.workshops.second.httpserver.exception;

import java.io.IOException;

public class InaccessibleFileException extends IOException {
    public InaccessibleFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
