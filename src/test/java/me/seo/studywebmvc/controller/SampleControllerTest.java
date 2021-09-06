package me.seo.studywebmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.seo.studywebmvc.dto.Person;
import me.seo.studywebmvc.formatter.PersonFormatter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import java.io.StringWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest({SampleController.class, PersonFormatter.class})
//통합 테스트
//@SpringBootTest
//@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    // spring Marshaller
    // Jaxb2Marshaller가 구현
    @Autowired
    Marshaller marshaller;

    @Test
    public void hello() throws Exception {
        this.mockMvc.perform(get("/hello/seo"))
                .andDo(print())
                .andExpect(content().string("hello seo"));
    }
    @Test
    public void hello2() throws Exception {
        this.mockMvc.perform(get("/hello2").param("name","seo"))
                .andDo(print())
                .andExpect(content().string("hello2 seo"));
    }

    @Test
    public void hellostatic() throws Exception {
        this.mockMvc.perform(get("/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("hello index")));

    }
    @Test
    public void helloMobilestatic() throws Exception {
        this.mockMvc.perform(get("/mobile/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("hello mobile")))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));

    }

    @Test
    public void jsonMessage() throws Exception {
        Person person = new Person();
        person.setName("seo");
        person.setId(10l);

        // 객체 -> string문자열의 json으로 변겅 jackson Object Mapper
        // 본문을 확인 할 때 json path 사용 가능
        // https://github.com/json-path/JsonPath
        // http://jsonpath.com/
        String jsonString  = objectMapper.writeValueAsString(person);
        this.mockMvc.perform(get("/Message")
                        .contentType(MediaType.APPLICATION_JSON_VALUE) // 요청하는 request type
                        .accept(MediaType.APPLICATION_JSON_VALUE) // 원하는 reponse type
                        .content(jsonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("seo"));
    }

    @Test
    // https://www.w3schools.com/xml/xpath_syntax.asp
    // https://www.freeformatter.com/xpath-tester.html
    // 스프링 부트 xml 의존성 추가해 주지 않음
    public void xmlMessage() throws Exception {
        Person person = new Person();
        person.setName("seo");
        person.setId(10l);

        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        marshaller.marshal(person,result);
        String xmlString = stringWriter.toString();

        this.mockMvc.perform(get("/Message")
                        .contentType(MediaType.APPLICATION_XML) // 요청하는 request type
                        .accept(MediaType.APPLICATION_XML) // 원하는 reponse type
                        .content(xmlString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("person/name").string("seo"))
                .andExpect(xpath("person/id").string("10"));
    }
}