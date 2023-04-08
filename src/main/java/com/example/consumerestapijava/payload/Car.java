package com.example.consumerestapijava.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Car implements Serializable {
    private static final long serialVersionUID = 3729608657530019376L;

    private String make;

    private String year;

    private String model;

    private String engine;
}
