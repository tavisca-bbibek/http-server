package com.tavisca.workshops.second.httpServer.util;

import com.tavisca.workshops.second.httpServer.exception.InvalidResourceFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourcePathParser {

    private static final String RESOURCE_PATH_PATTERN = "(.*)\\.([a-zA-Z0-9]*)";

    public static String parseExtension(String resourcePath) throws InvalidResourceFormatException{
        Pattern resourcePattern = Pattern.compile(RESOURCE_PATH_PATTERN);
        Matcher matcher =  resourcePattern.matcher(resourcePath);
        if(matcher.find()){
            return matcher.group(2);
        }else
            throw new InvalidResourceFormatException("Invalid resourcePath format: " + resourcePath);
    }
}
