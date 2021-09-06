package me.seo.studywebmvc.controller;

import me.seo.studywebmvc.dto.Person;
import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") Person person){
        return "hello " + person.getName();
    }
    @GetMapping("/hello2")
    public String hello2(@RequestParam("name") Person person){
        return "hello2 "+person.getName();
    }

    @GetMapping("/message")
    //@ResponseBody // 응답 본문에 넣어준다  // restcontroller 모든 요청에 reponsebody
    //@RequestBody //  요청 본문에서 메시지를 읽는다
    public String message(@RequestBody Person person){
        return "hello message";
    }

    @GetMapping("/Message")
    public Person Message(@RequestBody Person person){
        return person;
    }
}
