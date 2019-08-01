package com.tavisca.workshops.second.httpserver.util;


import com.tavisca.workshops.second.httpserver.Server;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    @Test
    void canReadContentsOfTheFile() {
        String filePath = Server.rootDirectory + "/" + Server.responsesDirectory + "/" + "fileNotFound.html";

        try {
            String data = FileHandler.readFile(filePath);
            assertEquals(
                    "<!DOCTYPE html>\r\n" +
                            "<html>\r\n" +
                            "    <body>\r\n" +
                            "        <h1>404 File Not Found</h1>\r\n" +
                            "        <p>Can't find that stupid file.</p>\r\n" +
                            "        <p>Click here for useless information.</p>\r\n" +
                            "    </body>\r\n" +
                            "</html>"
                    , data);
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    void throwsFileNotFoundExceptionWhenGivenInvalidFile(){
        String filePath = Server.rootDirectory + "/" + Server.responsesDirectory + "/" + "file.html";

       assertThrows(FileNotFoundException.class, () -> {
           String data = FileHandler.readFile(filePath);
        });
    }
}
