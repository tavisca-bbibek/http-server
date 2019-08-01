package com.tavisca.workshops.second.httpserver;

import com.tavisca.workshops.second.httpserver.exception.HttpRequestParseException;
import com.tavisca.workshops.second.httpserver.model.HttpRequest;
import com.tavisca.workshops.second.httpserver.model.RequestType;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {
    public static HttpRequest parse(String requestString) throws HttpRequestParseException {
        Pattern line = Pattern.compile("(.*)");
        Matcher lineMatcher = line.matcher(requestString);
        if(lineMatcher.find()){
            String firstLine = lineMatcher.group();
            String[] parts = firstLine.split(" ");

            RequestType requestType = getRequestTypeFor(parts[0]);
            //Removing the '/' at the beginning, eg. '/index.html' -> 'index.html'
            String resource = parts[1].length() > 1 ? parts[1].substring(1): "";

            String protocol = parts[2];
            return new HttpRequest(requestString, requestType, resource, protocol);
        }
        throw new HttpRequestParseException("Invalid HttpRequest format");
    }

    public static RequestType getRequestTypeFor(String requestTypeString) throws HttpRequestParseException {
        switch (requestTypeString){
            case "GET":
                return RequestType.GET;
            default:
                throw new HttpRequestParseException("Invalid HttpRequest method");
        }
    }
}
