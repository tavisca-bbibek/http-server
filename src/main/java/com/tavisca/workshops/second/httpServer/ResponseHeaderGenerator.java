package com.tavisca.workshops.second.httpServer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class ResponseHeaderGenerator {

    private static final String DEFAULT_PROTOCOL = "HTTP/1.1";
    private static final String DEFAULT_MIME_TYPE = "text/html";
    private static final String HEADER_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";

    static byte[] generate(String protocol, int statusCode, int contentLength, String mimeType) {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(protocol + " " + statusCode + " " + Server.statusCodeToStringMap.get(statusCode) + "\n")
                .append("Date: " +
                        getDateTimeString()
                        + "\n")
                .append("Server: Custom-Server/1.0\n")
                .append("Content-Length: " + contentLength + '\n')
                .append("Connection: Closed\n")
                .append("Content-Type: " + mimeType + "\n\n");
        return headerBuilder.toString().getBytes();
    }

    static byte[] generateFileNotFound(int contentLength){
        return generate(DEFAULT_PROTOCOL, 404, contentLength, DEFAULT_MIME_TYPE);
    }

    static byte[] generateClientError(int contentLength){
        return generate(DEFAULT_PROTOCOL, 500, contentLength, DEFAULT_MIME_TYPE);
    }

    static byte[] generateServerError(int contentLength){
        return generate(DEFAULT_PROTOCOL, 400, contentLength, DEFAULT_MIME_TYPE);
    }

    private static String getDateTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(HEADER_DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(new Date());
    }
}
