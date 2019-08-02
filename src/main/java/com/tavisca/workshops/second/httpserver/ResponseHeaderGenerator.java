package com.tavisca.workshops.second.httpserver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ResponseHeaderGenerator {

    private static final String HEADER_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";

    public static String generate(String protocol, int statusCode, int contentLength, String mimeType) {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(protocol + " " + statusCode + " " + Server.statusCodeToStringMap.get(statusCode) + "\n")
                .append("Date: " +
                        getDateTimeString()
                        + "\n")
                .append("Server: Custom-Server/1.0\n")
                .append("Content-Length: " + contentLength + '\n')
                .append("Connection: Closed\n")
                .append("Content-Type: " + mimeType + "\n\n");
        return headerBuilder.toString();
    }

    private static String getDateTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(HEADER_DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(new Date());
    }
}
