package com.example.scrapper.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
public class ScrapperControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testListProjectAggregatedFiles() throws Exception {
        URI uri = new URI("/api?project=aclaudiojj/forum-spring-api/tree/main/src/main/java/com/example/forum/controller/dto");

        mvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].extension").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].extension").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lines").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lines").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].bytes").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].bytes").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].count").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].count").isNotEmpty());
    }
}