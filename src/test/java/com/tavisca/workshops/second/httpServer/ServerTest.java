package com.tavisca.workshops.second.httpServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;

import static org.junit.Assert.fail;

class ServerTest {
    Server server;


    ServerTest() {
        server = Server.getInstance();
        Server.main(new String[]{});
    }

    @Test
    void respondsWithIndexPageForEmptyResourceRequest(){
        System.out.println("Entering Test for BaseURL");
        try (InputStream responseStream = new URL("localhost").openStream()){
            int responseSize = responseStream.available();
            byte[] responseData = new byte[responseSize];
            responseStream.readNBytes(responseData, 0, responseSize);

            System.out.println("Response is: ");
            System.out.write(responseData);

            //TODO: Validate the response.

        } catch (IOException e) {
            fail();
        }

    }

    /*String getRequestStringWith(String resourcePath){
        return "GET /" + resourcePath + " HTTP/1.1\n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\n" +
                "Host: www.tutorialspoint.com\n" +
                "Accept-Language: en-us\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Connection: Keep-Alive";
    }*/
}
