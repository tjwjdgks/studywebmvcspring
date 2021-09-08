package me.seo.studywebmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.seo.studywebmvc.dto.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    // jackson에서 제공해주는 api //  object <-> json
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createApiEvent() throws Exception {
        Event event = Event.builder().name("seo").limitOfEnrollment(10).build();

        // obejct -> string
        String json = objectMapper.writeValueAsString(event);
        mockMvc.perform(post("/api")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("seo"))
                .andExpect(jsonPath("$.limitOfEnrollment").value(10));
    }
    @Test
    public void createApiEvent2() throws Exception {
        Event event = Event.builder().name("seo").limitOfEnrollment(10).build();

        // obejct -> string
        String json = objectMapper.writeValueAsString(event);
        mockMvc.perform(post("/api/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("seo"))
                .andExpect(jsonPath("$.limitOfEnrollment").value(10));
    }
    @Test
    public void createApiEvent3() throws Exception {
        Event event = Event.builder().limitOfEnrollment(10).build();

        // obejct -> string
        String json = objectMapper.writeValueAsString(event);
        mockMvc.perform(post("/api/rest/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}