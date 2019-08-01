package com.tavisca.workshops.second.httpserver.model;

import java.util.Objects;

public class HttpRequest {
    private RequestType type;
    private String resource;
    private String protocol;

    private String requestString;

    public HttpRequest(String requestString, RequestType type, String resource, String protocol) {
        this.requestString = requestString;
        this.type = type;
        this.resource = resource;
        this.protocol = protocol;
    }

    public RequestType getType() {
        return type;
    }

    public String getResource() {
        return resource;
    }

    public String getRequestString() {
        return requestString;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest request = (HttpRequest) o;
        return type == request.type &&
                Objects.equals(resource, request.resource) &&
                Objects.equals(requestString, request.requestString);
    }
}
