package com.example.consumerestapijava.generics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class JsonMapper<T> {
    private final ObjectMapper objectMapper;

    public JsonMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public abstract String getJsonFilePath();

    private String getObjectJsonString() throws IOException {
        File resource = new ClassPathResource(getJsonFilePath()).getFile();
        return new String(Files.readAllBytes(resource.toPath()));
    }

    public T fromJson(Class<T> valueType) throws IOException {
        return objectMapper.readValue(getObjectJsonString(),valueType);
    }

    public String toJson(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
