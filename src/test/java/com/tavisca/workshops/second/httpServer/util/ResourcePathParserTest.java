package com.tavisca.workshops.second.httpServer.util;

import com.tavisca.workshops.second.httpServer.exception.InvalidResourceFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

class ResourcePathParserTest {

    @Test
    void canExtractExtensionFromResourceString() {
        String extension = null;
        try {
            extension = ResourcePathParser.parseExtension("www/resources/image.jpeg");
            assertEquals("jpeg", extension);
        } catch (InvalidResourceFormatException e) {
            fail();
        }
    }

}