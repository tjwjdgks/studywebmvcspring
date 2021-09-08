package me.seo.studywebmvc.controller;


import me.seo.studywebmvc.dto.UserHandle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.logging.Handler;

@Controller
public class HandlerController {



    // uri 패턴
    @GetMapping("/handle/{id}")
    @ResponseBody
    public UserHandle getHandle(@PathVariable("id") Integer idValue){ // 이름이 다른 경우 명시해야한다
        UserHandle handle = new UserHandle();
        handle.setId(idValue);
        return handle;
    }


    // @RequestParam
    @PostMapping("/handle")
    @ResponseBody
    public UserHandle getHandle2(@RequestParam String name,@RequestParam Integer id){ // ?name=seo 이런 식을 들어온다 // 생략도 가능
        UserHandle user = new UserHandle();
        user.setName(name);
        user.setId(id);
        return user;
    }
    @PostMapping("/handles")
    @ResponseBody
    public UserHandle getHandle(@RequestParam Map<String,String> map){
        UserHandle user = new UserHandle();
        user.setName(map.get("name"));
        user.setId(Integer.parseInt(map.get("id")));
        return user;
    }

    @GetMapping("/handle/form")
    public String handleForms(Model model){
        UserHandle test = new UserHandle();
        test.setId(0);
        test.setName("default");
        model.addAttribute("handle",test); // handle/form -> handles 객체가 default form을 채워줄 form backing object
        return "handle/form";
    }

    @ResponseBody
    @GetMapping("/handle/form/modelAttribute")
    // BindingResult 바인딩 에러를 직접 다루고 싶은 경우
    // 복합 type modelAttribute 생략 가능
    // valid, validation 어노테이션 사용 가능
    // valid group 지정 x , but validation group 지정 o // validateLimit 만 검증
    public UserHandle handleFormModel(@Validated(UserHandle.ValidateLimit.class) @ModelAttribute UserHandle handle, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("binding error");
        }
        return handle;
    }
}
