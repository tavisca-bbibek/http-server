package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.util.DateTimeGenerator;

public class ResponseHeaderGenerator {

    public static final String HEADER_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";

    public static String generate(String protocol, int statusCode, int contentLength, String mimeType) {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(protocol + " " + statusCode + " " + Server.statusCodeToStringMap.get(statusCode) + "\n")
                .append("Date: " +
                        DateTimeGenerator.generate(HEADER_DATE_FORMAT, "GMT")
                        + "\n")
                .append("Server: Custom-Server/1.0\n")
                .append("Content-Length: " + contentLength + '\n')
                .append("Connection: Closed\n")
                .append("Content-Type: " + mimeType + "\n\n");
        return headerBuilder.toString();
    }
}
