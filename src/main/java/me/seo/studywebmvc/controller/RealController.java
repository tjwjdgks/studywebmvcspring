package me.seo.studywebmvc.controller;

import me.seo.studywebmvc.exception.CustomEexception;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class RealController {
    // ExceptionHandler
    // argument https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-exceptionhandler
    // 익셉션 핸들러, 가장 구체적인 type이 mapping 된다 // 컨트롤러 안에서 특정한 예외 발생시
    @ExceptionHandler //({... ,... }) 여러개도 가능 but 파라미터들이 포함하는 상위 type으로 받아야한다
    public String customErrorHandler(CustomEexception exception, Model model){
        model.addAttribute("message","custom error");
        return "error";
    }

    // 바인더
    @InitBinder // 컨트롤러 안에서 바인딩
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setAllowedFields("id");
       System.out.println("testtest");
    }
    @ResponseBody
    @GetMapping("test/test")
    public String test(@RequestParam Integer id){
        System.out.println(id);
        return "hello";
    }
    @GetMapping("test/error")
    public String testError(){
        throw  new CustomEexception();
    }
}
