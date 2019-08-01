package com.tavisca.workshops.second.httpserver.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceHandlerTest {

    @Test
    void canExtractExtensionFromResourceString(){
            String extension = ResourceHandler.parseType("www/resources/image.jpeg");
            assertEquals("jpeg", extension);
    }

}