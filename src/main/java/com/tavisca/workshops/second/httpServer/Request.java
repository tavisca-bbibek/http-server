package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.exception.InvalidResourceFormatException;
import com.tavisca.workshops.second.httpServer.model.RequestMethod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private RequestMethod method;
    private String resourcePath;
    private String protocol;
    private static final String RESOURCE_PATH_PATTERN = "(.*)\\.([a-zA-Z0-9]*)";


    private static final Map<String, String> extensionToMimeMap = Collections.unmodifiableMap(
            new HashMap<String, String>(){{
                put("html", "text/html");
                put("htm", "text/html");
                put("js", "text/text/javascript");
                put("css", "text/css");
                put("jpeg", "image/jpeg");
                put("jpg", "image/jpeg");
                put("png", "image/png");
                put("ico", "image/x-icon");
                put("txt", "plain/text");
                put("otf", "font/otf");
                put("ttf", "font/ttf");
                put("svg", "image/svg+xml");
                put("eot", "application/vnd.ms-fontobject");
                put("woff", "font/woff");
                put("woff2", "font/woff2");
            }});


    public Request(RequestMethod method, String resourcePath, String protocol) {
        this.method = method;
        this.resourcePath = resourcePath;
        this.protocol = protocol;
    }

    String getMimeType() throws InvalidResourceFormatException {
        Pattern resourcePattern = Pattern.compile(RESOURCE_PATH_PATTERN);
        Matcher matcher = resourcePattern.matcher(resourcePath);
        if (matcher.find()) {
            String extension = matcher.group(2);
            return extensionToMimeMap.get(extension);
        } else
            throw new InvalidResourceFormatException("Invalid resourcePath format: " + resourcePath);
    }


    void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    String getResourcePath() {
        return resourcePath;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return method == request.method &&
                Objects.equals(resourcePath, request.resourcePath);
    }
}
