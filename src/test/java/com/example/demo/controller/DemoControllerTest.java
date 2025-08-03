package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

/**
 * Skeleton template for a controller test using MockMvc.
 *
 * You can use annotations from JUnit 5 such as @ParameterizedTest, @ValueSauce,
 * @CsvSource and @MethodSource for your test data.
 *
 * Example usage of mockMvc for a GET request
 * mockMvc.perform(get("/path-to-your-endpoint").param("your-query-param", param-value))
 *                 .andExpect(status().whateverStatusCodeYouExpect())
 *                 .andExpect(content().string("string-you-expect-in-response")).
 *                 .andExpect(jsonPath("$.jsonField").value("json-value-you-expect"));
 */
@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String REMOVE_URL = "/remove";
    private static final String QUERY_PARAM_NAME = "original";

    @ParameterizedTest
    @CsvSource({
            "person,erso",
            "eloquent,loquen",
            "country,ountr",
            "xyz,y",
            "ab,''",
            "123%qwerty+,23%qwerty",
            "1234,23",
            "@#,''"
    })
    void testRemove_ValidInputs(String input, String expected) throws Exception {
        mockMvc.perform(get(REMOVE_URL).param(QUERY_PARAM_NAME, input))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a"})
    void testRemove_InvalidInputs(String input) throws Exception {
        mockMvc.perform(get(REMOVE_URL).param(QUERY_PARAM_NAME, input))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Minimum 2 characters are required."));
    }

}
