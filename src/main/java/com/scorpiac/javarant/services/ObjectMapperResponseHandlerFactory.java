package com.scorpiac.javarant.services;

import org.apache.http.client.ResponseHandler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.InputStream;
import java.util.Scanner;

@Singleton
class ObjectMapperResponseHandlerFactory {
    private final ObjectMapperService mapperService;

    @Inject
    ObjectMapperResponseHandlerFactory(ObjectMapperService mapperService) {
        this.mapperService = mapperService;
    }

    /**
     * Get a response handler which maps the response to a class.
     *
     * @param clazz The class to map the response to.
     * @param <T>   The type of the class to map the response to.
     * @return A response handler.
     */
    <T> ResponseHandler<T> getResponseHandler(Class<T> clazz) {
        return response -> {
            InputStream stream = response.getEntity().getContent();
            String content = streamToString(stream);
            return mapperService.getMapper().readValue(content, clazz);
        };
    }

    /**
     * Read a stream into a string.
     *
     * @param stream The stream to read.
     * @return The contents of the stream as a string.
     */
    private static String streamToString(InputStream stream) {
        try (Scanner s = new Scanner(stream).useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "";
        }
    }
}
