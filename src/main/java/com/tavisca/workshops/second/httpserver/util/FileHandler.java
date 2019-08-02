package com.tavisca.workshops.second.httpserver.util;

import com.tavisca.workshops.second.httpserver.Server;
import com.tavisca.workshops.second.httpserver.exception.InaccessibleFileException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileHandler {
    public static byte[] readFile(String fileName) throws FileNotFoundException, InaccessibleFileException {
        String filePath = Server.DIRECTORY_ROOT + '/' + fileName;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        try {
            return fileInputStream.readAllBytes();
        } catch (IOException e) {
            throw new InaccessibleFileException("Can't Read file - " + fileName, e);
        }
    }
}
