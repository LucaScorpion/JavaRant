package com.scorpiac.javarant.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

@Singleton
public class ObjectMapperService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public ObjectMapper getMapper() {
        return MAPPER;
    }
}
