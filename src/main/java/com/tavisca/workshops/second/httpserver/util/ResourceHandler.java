package com.tavisca.workshops.second.httpserver.util;

import com.tavisca.workshops.second.httpserver.exception.InvalidResourceFormatException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceHandler {

    public static final String RESOURCE_PATTERN = "(.*)\\.([a-zA-Z0-9]*)";
    public static final Map<String, String> extensionToMimeMap = Collections.unmodifiableMap(
            new HashMap<String, String>(){{
                put("html", "text/html");
                put("htm", "text/html");
                put("jpeg", "image/jpeg");
                put("jpg", "image/jpeg");
                put("png", "image/png");
                put("ico", "image/x-icon");
                put("txt", "plain/text");
            }}
    );

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
