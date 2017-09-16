package com.scorpiac.javarant.services;

import org.testng.annotations.Test;

import java.net.URI;

import static org.testng.Assert.assertEquals;

public class RequestHandlerTest {
    @Test
    public void testResolve() {
        assertEquals(
                new RequestHandler(new ObjectMapperResponseHandlerFactory(new ObjectMapperService()))
                        .resolve("/some-endpoint"),
                URI.create("https://www.devrant.io/some-endpoint")
        );
    }
}
