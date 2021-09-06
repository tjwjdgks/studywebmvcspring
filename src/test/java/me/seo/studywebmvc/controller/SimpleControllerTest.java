package me.seo.studywebmvc.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(SimpleController.class)
class SimpleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hellos() throws Exception {

        mockMvc.perform(get("/hellos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hellos"));

        mockMvc.perform(post("/hellos"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());

    }
    // uri 패턴 매핑
    @Test
    public void pattern() throws Exception {

        mockMvc.perform(get("/test/t1"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/test/t2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void pattern2() throws Exception {
        mockMvc.perform(get("/hellos/seo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(SimpleController.class))
                .andExpect(handler().methodName("helloname"))
                .andExpect(content().string("hello seo"));

    }
    // 컨텐츠 타입 맵핑
    @Test
    public void pattern3() throws Exception {
        mockMvc.perform(get("/helloseo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
    // 헤더 맵핑
    @Test
    public void pattern4() throws Exception {
        mockMvc.perform(get("/helloheader")
                        .header(HttpHeaders.FROM,"localhost"))
                .andDo(print())
                .andExpect(status().isOk());

    }
    // 파라미터 맵핑
    @Test
    public void pattern5() throws Exception {
        mockMvc.perform(get("/helloparam")
                        .param("name","seo "))
                .andDo(print())
                .andExpect(status().isOk());

    }

    //HEAD와 OPTIONS 요청 처리
    @Test
    public void pattern6() throws Exception {
        // head get요청과 동일하지만  응답헤더만 가져온다
        mockMvc.perform(head("/headoroption"))
                .andDo(print())
                .andExpect(status().isOk());
        // 사용할 수 있는 http method 제공
        mockMvc.perform(options("/headoroption"))
                .andDo(print())
                .andExpect(status().isOk());

    }
    //커스텀 애노테이션
    @Test
    public void pattern7() throws Exception {
       mockMvc.perform(options("/hellomapping"))
        .andDo(print())
        .andExpect(status().isOk());

    }
}