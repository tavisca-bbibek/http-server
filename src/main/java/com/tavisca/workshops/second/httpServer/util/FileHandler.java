package com.tavisca.workshops.second.httpServer.util;

import com.tavisca.workshops.second.httpServer.Server;
import com.tavisca.workshops.second.httpServer.exception.InaccessibleFileException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileHandler {
    private static final String DIRECTORY_ROOT = "www/";

    public static byte[] readFile(String fileName) throws FileNotFoundException, InaccessibleFileException {
        String filePath = DIRECTORY_ROOT + fileName;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        try {
            return fileInputStream.readAllBytes();
        } catch (IOException e) {
            throw new InaccessibleFileException("Can't Read file - " + fileName, e);
        }
    }
}
