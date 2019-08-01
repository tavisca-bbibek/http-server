package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.exception.HttpRequestParseException;
import com.tavisca.workshops.second.httpserver.model.HttpRequest;
import com.tavisca.workshops.second.httpserver.model.RequestType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.fail;

class HttpRequestParserTest {

    @Test
    void canParseRequest() {

        HttpRequest request = null;
        try {
            request = HttpRequestParser.parse("GET /index.html HTTP/1.1\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134\n" +
                    "Accept-Language: en-US\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                    "Upgrade-Insecure-Requests: 1\n" +
                    "Accept-Encoding: gzip, deflate\n" +
                    "Host: localhost\n" +
                    "Connection: Keep-Alive");
            assertEquals(new HttpRequest("GET /index.html HTTP/1.1\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134\n" +
                    "Accept-Language: en-US\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                    "Upgrade-Insecure-Requests: 1\n" +
                    "Accept-Encoding: gzip, deflate\n" +
                    "Host: localhost\n" +
                    "Connection: Keep-Alive", RequestType.GET, "index.html", "HTTP/1.1"), request);
        } catch (HttpRequestParseException e) {
            fail();
        }
    }
}