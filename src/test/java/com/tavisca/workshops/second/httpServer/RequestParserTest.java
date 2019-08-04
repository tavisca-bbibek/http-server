package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.exception.RequestParseException;
import com.tavisca.workshops.second.httpServer.model.RequestMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.fail;

class RequestParserTest {

    @Test
    void canParseRequest() {

        Request request;
        try {
            request = RequestParser.parse("GET /index.html HTTP/1.1\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134\n" +
                    "Accept-Language: en-US\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                    "Upgrade-Insecure-Requests: 1\n" +
                    "Accept-Encoding: gzip, deflate\n" +
                    "Host: localhost\n" +
                    "Connection: Keep-Alive");
            assertEquals(new Request(RequestMethod.GET, "index.html", "HTTP/1.1"), request);
        } catch (RequestParseException e) {
            fail();
        }
    }
}