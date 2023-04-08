package com.example.consumerestapijava;

import com.example.consumerestapijava.generics.CarJsonMapper;
import com.example.consumerestapijava.generics.JsonMapper;
import com.example.consumerestapijava.generics.PersonJsonMapper;
import com.example.consumerestapijava.payload.Car;
import com.example.consumerestapijava.payload.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;

@SpringBootApplication
@Slf4j
public class ConsumeRestApiJavaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumeRestApiJavaApplication.class, args);
    }
}
