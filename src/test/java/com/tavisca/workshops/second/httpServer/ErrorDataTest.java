package com.tavisca.workshops.second.httpServer;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ErrorDataTest {

    @Test
    void canReturnFileNotFoundData() {
        try {
            byte[] expected = readAllBytes(new FileInputStream("www/errorPages/HTTP404.html"));
            assertArrayEquals(expected, ErrorData.fileNotFound());
        } catch (IOException e) {
           fail();
        }
    }

    @Test
    void canReturnServerErrorFoundData() {
        try {
            byte[] expected = readAllBytes(new FileInputStream("www/errorPages/HTTP500.html"));
            assertArrayEquals(expected, ErrorData.serverError());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void canReturnClientErrorFoundData() {
        try {
            byte[] expected = readAllBytes(new FileInputStream("www/errorPages/HTTP400.html"));
            assertArrayEquals(expected, ErrorData.clientError());
        } catch (IOException e) {
            fail();
        }
    }

    private static byte[] readAllBytes(FileInputStream fileInputStream) throws IOException{
        int size = fileInputStream.available();
        byte[] contents = new byte[size];
        fileInputStream.read(contents, 0, size);
        return contents;
    }

}