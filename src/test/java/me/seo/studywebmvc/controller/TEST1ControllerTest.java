package me.seo.studywebmvc.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TEST1Controller.class)
class TEST1ControllerTest1 {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void test1() throws Exception {
        mockMvc.perform(get("/test1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void test2() throws Exception {
        mockMvc.perform(get("/test2/1"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/test2/2"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/test2/3"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void test3() throws Exception {
        mockMvc.perform(post("/test3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void test4() throws Exception {
        mockMvc.perform(delete("/test4/1"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(delete("/test4/2"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(delete("/test4/3"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void test5() throws Exception {
        mockMvc.perform(put("/test5/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(put("/test5/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}