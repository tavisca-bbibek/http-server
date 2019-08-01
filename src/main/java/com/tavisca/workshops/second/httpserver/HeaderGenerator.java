package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.HttpServer;
import com.tavisca.workshops.second.httpserver.model.HttpRequest;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class HeaderGenerator {
    public static String generate(String protocol, int statusCode, int payloadLength, String contentType) {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(protocol + " " + statusCode + " " + HttpServer.statusCodeToStringMap.get(statusCode) + "\n")
                .append("Date: " +
                        getCurrentDateTimeString()
                        + "\n")
                .append("Server: Custom-Server/1.0\n")
                .append("Content-Length: " + payloadLength + '\n')
                .append("Connection: Closed\n")
                .append("Content-Type: " + contentType + "\n\n");
        return headerBuilder.toString();
    }

    private static String getCurrentDateTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(new Date());
    }
}
