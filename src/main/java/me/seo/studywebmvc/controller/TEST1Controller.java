package me.seo.studywebmvc.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class TEST1Controller {

    @GetMapping("/test1")
    public String getTest(){
        return "test";
    }
    @GetMapping("/test2/*")
    public String getTest2(){
        return "test2";
    }

    @PostMapping(value = "/test3",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String postTest3(){
        return "test3";
    }
    @DeleteMapping("/test4/{id}")
    public String deleteTest4(@PathVariable int id){
        return "test4";
    }
    @PutMapping(value = "/test5/*",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String putTest5(){
        return "test5";
    }
}
