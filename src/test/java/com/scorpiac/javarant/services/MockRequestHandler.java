package com.scorpiac.javarant.services;

import java.net.URI;

public class MockRequestHandler extends RequestHandler {
    private final URI local;

    public MockRequestHandler(int port) {
        super(new ObjectMapperResponseHandlerFactory(new ObjectMapperService()));
        local = URI.create("http://localhost:" + port);
    }

    @Override
    URI resolve(String endpoint) {
        return local.resolve(endpoint);
    }
}
