package com.example.consumerestapijava.generics;

import org.springframework.stereotype.Component;

@Component
public class CarJsonMapper<T> extends JsonMapper<T>{
    private static final String CAR_JSON_PATH= "json/car.json";
    @Override
    public String getJsonFilePath() {
        return CAR_JSON_PATH;
    }
}
