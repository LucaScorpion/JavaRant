package com.scorpiac.javarant.services;

import org.apache.http.client.ResponseHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ObjectMapperResponseHandlerFactory {
    private final ObjectMapperService mapperService;

    @Inject
    public ObjectMapperResponseHandlerFactory(ObjectMapperService mapperService) {
        this.mapperService = mapperService;
    }

    /**
     * Get a response handler which maps the response to a class.
     *
     * @param clazz The class to map the response to.
     * @param <T>   The type of the class to map the response to.
     * @return A response handler.
     */
    public <T> ResponseHandler<T> getResponseHandler(Class<T> clazz) {
        return response -> mapperService.getMapper().readValue(response.getEntity().getContent(), clazz);
    }
}
