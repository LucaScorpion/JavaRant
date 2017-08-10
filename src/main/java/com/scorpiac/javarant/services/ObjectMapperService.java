package com.scorpiac.javarant.services;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

@Singleton
public class ObjectMapperService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    /**
     * Get the object mapper.
     */
    public ObjectMapper getMapper() {
        return MAPPER;
    }
}
