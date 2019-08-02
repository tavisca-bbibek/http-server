package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.exception.RequestParseException;
import com.tavisca.workshops.second.httpserver.model.Request;
import com.tavisca.workshops.second.httpserver.model.RequestMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {

    private static final String PATTERN_LINE_GROUP = "(.*)";
    private static final String MESSAGE_INVALID_REQUEST_FORMAT = "Invalid HttpRequest format";
    private static final String MESSAGE_INVALID_REQUEST_METHOD = "Invalid HttpRequest method";

    public static Request parse(String requestString) throws RequestParseException {
        Pattern line = Pattern.compile(PATTERN_LINE_GROUP);
        Matcher lineMatcher = line.matcher(requestString);
        if(lineMatcher.find()){
            String firstLine = lineMatcher.group();
            String[] parts = firstLine.split(" ");

            RequestMethod requestMethod = getRequestTypeFor(parts[0]);

            //Removing the '/' at the beginning, eg. '/index.html' -> 'index.html'
            String resource = parts[1].length() > 1 ? parts[1].substring(1): "";

            String protocol = parts[2];
            return new Request(requestMethod, resource, protocol);
        }
        throw new RequestParseException(MESSAGE_INVALID_REQUEST_FORMAT);
    }

    private static RequestMethod getRequestTypeFor(String requestMethod) throws RequestParseException {
        if ("GET".equals(requestMethod)) {
            return RequestMethod.GET;
        }
        throw new RequestParseException(MESSAGE_INVALID_REQUEST_METHOD);
    }
}
