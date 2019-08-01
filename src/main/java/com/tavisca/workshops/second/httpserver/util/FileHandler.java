package com.tavisca.workshops.second.httpserver.util;

import com.tavisca.workshops.second.httpserver.HttpServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileHandler {
    public static String readFile(String fileName) throws FileNotFoundException {
        String filePath = HttpServer.rootDirectory + '/' + fileName;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        try {
            return new String(fileInputStream.readAllBytes());
        } catch (IOException e) {
            return null;
        }
    }
}
