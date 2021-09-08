package me.seo.studywebmvc.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FormController.class)
class FormControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void Test1() throws Exception {
        // return 받을 수 있음
        ResultActions resultActions = mockMvc.perform(post("/postings")
                        .param("Id", "-10")
                        .param("name", "seo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
        ModelAndView modelAndView = resultActions.andReturn().getModelAndView();
        // binding result 가 기본으로 model에 들어가 있음
        Map<String, Object> model = modelAndView.getModel();
        System.out.println(model.size());
    }
    @Test
    public void Test2() throws Exception{
        // session test
        /*
        mockMvc.perform(get("/form/session")
                    .param("name","seo")
                    .param("id","10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("userForm",notNullValue()));

         */
        // 구체적으로 보고 싶을 경우
        MockHttpServletRequest request = mockMvc.perform(get("/form/session")
                        .param("name", "seo")
                        .param("id", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("userForm", notNullValue()))
                .andReturn().getRequest();
        Object userForm = request.getSession().getAttribute("userForm");
        System.out.println(userForm );

    }

    // xpath
    //https://www.w3schools.com/xml/xpath_syntax.asp
    //https://www.freeformatter.com/xpath-tester.html#ad-output
    @Test
    public void getForm() throws Exception {
        mockMvc.perform(get("/form/list")
                    .sessionAttr("visitTime", LocalDateTime.now())
                    .flashAttr("flash","seo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories")) // 카테고리 key modelAttribute가 있는지 확인
                .andExpect(xpath("//p").nodeCount(1));
    }
}