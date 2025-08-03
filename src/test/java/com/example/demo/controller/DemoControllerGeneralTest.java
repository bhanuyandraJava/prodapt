package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
class DemoControllerGeneralTest {
    public static final String QUERY_PARAM_NAME = "original";
    public static final String REMOVE_URL = "/remove";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRemoveWithDifferentLengthParam() throws Exception {

        Map<String, String> testData = Map.of(
                "person", "erso",
                "eloquent", "loquen",
                "country", "ountr"
        );

        for (Map.Entry<String, String> entry : testData.entrySet()) {
            mockMvc.perform(get(REMOVE_URL).param(QUERY_PARAM_NAME, entry.getKey()))
                    .andExpect(status().isOk())
                    .andExpect(content().string(entry.getValue()));
        }
    }

    @Test
    public void testRemoveWithLengthThreeCharacters() throws Exception {

        String inputParamValue = "xyz";
        String expectedValue = "y";

        mockMvc.perform(get(REMOVE_URL).param(QUERY_PARAM_NAME, inputParamValue))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedValue));
    }

    @Test
    public void testRemoveWithEmptyParam() throws Exception {

        String emptyInputParamValue = "";

        mockMvc.perform(get(REMOVE_URL).param(QUERY_PARAM_NAME, emptyInputParamValue))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRemoveWithOneCharacterParam() throws Exception {
        String singleCharacterParamValue = "x";
        mockMvc.perform(get(REMOVE_URL).param(QUERY_PARAM_NAME, singleCharacterParamValue))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRemoveWithOneCharactersParam() throws Exception {
        String inputParamValue = "xy";
        String expectedValue = "";
        mockMvc.perform(get(REMOVE_URL).param(QUERY_PARAM_NAME, inputParamValue))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedValue));
    }

    @Test
    public void testRemoveWithSpecialCharacters() throws Exception {
        String inputParamValue = "123%qwerty+";
        String expectedValue = "23%qwerty";
        mockMvc.perform(get(REMOVE_URL).param(QUERY_PARAM_NAME, inputParamValue))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedValue));
    }

    @Test
    public void testRemoveWithTwoSpecialCharacters() throws Exception {
        String inputParamValue = "@#";
        String expectedValue = "";
        mockMvc.perform(get(REMOVE_URL).param(QUERY_PARAM_NAME, inputParamValue))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedValue));
    }

    @Test
    public void testRemoveWithNumericCharacters() throws Exception {
        String inputParamValue = "1234";
        String expectedValue = "23";
        mockMvc.perform(get(REMOVE_URL).param(QUERY_PARAM_NAME, inputParamValue))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedValue));
    }

}
