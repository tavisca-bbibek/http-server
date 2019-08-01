package com.tavisca.workshops.second.httpserver;

import org.junit.jupiter.api.Test;

class HeaderGeneratorTest {

    @Test
    void canGenerateHeaderWithOKStatus(){
        String header = HeaderGenerator.generate("HTTP/1.1", 200, 8, "text/plain");
        System.out.println(header);
    }

}