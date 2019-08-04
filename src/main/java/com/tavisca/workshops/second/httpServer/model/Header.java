package com.tavisca.workshops.second.httpServer.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class Header {

    private static final String HEADER_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";
    private static final int DEFAULT_STATUS_CODE = 200;
    private static final Map<Integer, String> statusCodeToStringMap = Map.of(
            200, "OK",
            400, "Bad Request",
            404, "Not Found",
            500, "Internal Server Error");
    private static final String DEFAULT_PROTOCOL = "HTTP/1.1";
    private static final String DEFAULT_MIME_TYPE = "text/html";

    private String protocol;
    private int statusCode;
    private Date date;
    private int contentLength;
    private String mimeType;

    public Header(int contentLength) {
        this(DEFAULT_PROTOCOL, DEFAULT_STATUS_CODE, contentLength, DEFAULT_MIME_TYPE);
    }

    public Header(int contentLength, String mimeType){
        this(DEFAULT_PROTOCOL, DEFAULT_STATUS_CODE, contentLength, mimeType);
    }

    public Header(String protocol, int statusCode, int contentLength, String mimeType) {
        this(protocol, statusCode, new Date(), contentLength, mimeType);
    }

    public Header(String protocol, int statusCode, Date date, int contentLength, String mimeType) {
        this.protocol = protocol;
        this.mimeType = mimeType;
        this.statusCode = statusCode;
        this.contentLength = contentLength;
        this.date = date;
    }

    public String toString() {
        String header = protocol + " " + statusCode + " " + statusCodeToStringMap.get(statusCode) + "\n" +
                "Date: " +
                getDateTimeString()
                + "\n" +
                "Server: Custom-Server/1.0\n" +
                "Content-Length: " + contentLength + '\n' +
                "Connection: Closed\n" +
                "Content-Type: " + mimeType + "\n\n";
        return header;
    }

    public byte[] getBytes() {
        return toString().getBytes();
    }

    public byte[] fileNotFound() {
        statusCode = 404;
        return getBytes();
    }

    public byte[] clientError() {
        statusCode = 500;
        return getBytes();
    }

    public byte[] serverError() {
        statusCode = 400;
        return getBytes();
    }

    private String getDateTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(HEADER_DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }
}
