package com.example.consumerestapijava.generics;

import com.example.consumerestapijava.payload.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonMapperTest {

    @Test
    void testFromJson() throws IOException {
        String json = "{\"name\":\"John\",\"age\":30,\"email\":\"john@example.com\"}";
        JsonMapper<Person> mapper = new JsonMapper<>() {
            @Override
            public String getJsonFilePath() {
                return "json/person.json";
            }
        };

        Person person = mapper.fromJson(Person.class);

        assertNotNull(person);
        assertEquals("John", person.getName());
        assertEquals(30, person.getAge());
        assertEquals("john@example.com", person.getEmail());
    }

    @Test
    void testToJson() throws JsonProcessingException {
        Person person = new Person("John", 30, "john@example.com");
        JsonMapper<Person> mapper = new JsonMapper<>() {
            @Override
            public String getJsonFilePath() {
                return null;
            }
        };

        // When
        String json = mapper.toJson(person);

        // Then
        assertNotNull(json);
        assertEquals("{\"name\":\"John\",\"age\":30,\"email\":\"john@example.com\"}", json);
    }
}