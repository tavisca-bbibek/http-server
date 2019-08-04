package com.tavisca.workshops.second.httpServer;

import com.tavisca.workshops.second.httpServer.exception.InvalidResourceFormatException;
import com.tavisca.workshops.second.httpServer.model.RequestMethod;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private RequestMethod method;
    private String resourcePath;
    private String protocol;
    private static final String RESOURCE_PATH_PATTERN = "(.*)\\.([a-zA-Z0-9]*)";


    private static final Map<String, String> extensionToMimeMap = Map.ofEntries(Map.entry("html", "text/html"),
                Map.entry("htm", "text/html"),
                Map.entry("js", "text/text/javascript"),
                Map.entry("css", "text/css"),
                Map.entry("jpeg", "image/jpeg"),
                Map.entry("jpg", "image/jpeg"),
                Map.entry("png", "image/png"),
                Map.entry("ico", "image/x-icon"),
                Map.entry("txt", "plain/text"),
                Map.entry("otf", "font/otf"),
                Map.entry("ttf", "font/ttf"),
                Map.entry("svg", "image/svg+xml"),
                Map.entry("eot", "application/vnd.ms-fontobject"),
                Map.entry("woff", "font/woff"),
                Map.entry("woff2", "font/woff2"));


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
