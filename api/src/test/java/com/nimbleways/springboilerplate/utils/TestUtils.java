package com.nimbleways.springboilerplate.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nimbleways.springboilerplate.entities.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestUtils {
    public TestUtils() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static List<Product> readJsonFile(String fileName, Class<?> testClass) {
        try (InputStream resourceAsStream = testClass.getResourceAsStream(fileName)) {
            assert resourceAsStream != null : "File '" + fileName + "' not found in resources";

            return objectMapper.readValue(resourceAsStream, new TypeReference<>() {
            });
        } catch (IOException exception) {
            throw new IllegalArgumentException("IOException thrown while reading test file: ", exception);
        }
    }

}
