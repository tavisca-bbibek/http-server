package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.exception.InaccessibleFileException;
import com.tavisca.workshops.second.httpServer.util.FileHandler;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ErrorDataTest {

    @Test
    void canReturnFileNotFoundData() {
        try {
            byte[] expected = new FileInputStream("www/responses/fileNotFound.html").readAllBytes();
            assertArrayEquals(expected, ErrorData.fileNotFound());
        } catch (IOException e) {
           fail();
        }
    }

    @Test
    void canReturnServerErrorFoundData() {
        try {
            byte[] expected = new FileInputStream("www/responses/serverError.html").readAllBytes();
            assertArrayEquals(expected, ErrorData.serverError());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void canReturnClientErrorFoundData() {
        try {
            byte[] expected = new FileInputStream("www/responses/clientError.html").readAllBytes();
            assertArrayEquals(expected, ErrorData.clientError());
        } catch (IOException e) {
            fail();
        }
    }

}