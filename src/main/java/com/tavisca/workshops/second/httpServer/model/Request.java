package com.tavisca.workshops.second.httpServer.model;

import java.util.Objects;

public class Request {
    private RequestMethod method;
    private String resourcePath;
    private String protocol;

    public Request(RequestMethod method, String resourcePath, String protocol) {
        this.method = method;
        this.resourcePath = resourcePath;
        this.protocol = protocol;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourcePath() {
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
