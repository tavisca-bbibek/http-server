package com.tavisca.workshops.second.httpserver.util;

import com.tavisca.workshops.second.httpserver.exception.InvalidResourceFormatException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceHandler {

    private static final String RESOURCE_PATTERN = "(.*)\\.([a-zA-Z0-9]*)";
    private static final Map<String, String> extensionToMimeMap = Map.of(
            "html", "text/html",
            "htm", "text/html",
            "jpeg", "image/jpeg",
            "jpg", "image/jpeg",
            "png", "image/png",
            "ico", "image/x-icon",
            "txt", "plain/text");

    private static String getExtension(String resource) throws InvalidResourceFormatException{
        Pattern resourcePattern = Pattern.compile(RESOURCE_PATTERN);
        Matcher matcher =  resourcePattern.matcher(resource);
        if(matcher.find()){
            return matcher.group(2);
        }else
            throw new InvalidResourceFormatException("Invalid resource format: " + resource);
    }

    public static String getMimeType(String resource) throws InvalidResourceFormatException {
        return extensionToMimeMap.get(getExtension(resource));
    }
}
