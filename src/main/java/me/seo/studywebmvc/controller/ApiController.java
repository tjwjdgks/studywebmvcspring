package me.seo.studywebmvc.controller;


import me.seo.studywebmvc.dto.Event;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping
    // 이 경우 본문의 body 정보만 가져올 수 있음
    // 요청 본문에 들어오는 Event를 받는다
    // 요청에 본문에 있는 데이터 Event로 컨버젼하려고 한다 // httpMessageConverter 에서 컨버젼 해준다
    public Event createEvent(@RequestBody Event event){ // @Vaild ,BindingResult 가능
        return event;
    }

    // 헤더정보까지 접근
    @PostMapping("/all")
    public Event createEvent(HttpEntity<Event> event){
        MediaType contentType = event.getHeaders().getContentType();
        System.out.println(contentType);
        return event.getBody();
    }

    @PostMapping("/rest")
    @ResponseBody // RestController 에서는  생략가능
    // http응답 본문에 담아준다, HttpMessageConverter 사용한다
    // accept header를 참고한다
    // spring boot는 웹 브라우저에서 어떠한 요청을 보내면 기본적으로 html or xml을 원한다는 accept header 들어간다
    // 그렇지 않을 경우는 json을 보내준다
    public Event backEvent(@RequestBody Event event){ // @Vaild ,BindingResult 가능
        return event;
    }

    @PostMapping("/rest/all")
    public ResponseEntity<Event> backEvent2(@RequestBody @Valid Event event, BindingResult result){
         if(result.hasErrors())
             // 빌드를 해야한다
             return ResponseEntity.badRequest().build();
        // ok(body)인 경우 build 필요 x
        return ResponseEntity.ok(event);
    }
}
