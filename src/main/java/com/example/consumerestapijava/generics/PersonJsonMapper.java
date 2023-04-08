package com.example.consumerestapijava.generics;

import org.springframework.stereotype.Component;

@Component
public class PersonJsonMapper<T> extends JsonMapper<T> {
    private static final String PERSON_JSON_PATH= "json/person.json";

    @Override
    public String getJsonFilePath() {
        return PERSON_JSON_PATH;
    }
}
