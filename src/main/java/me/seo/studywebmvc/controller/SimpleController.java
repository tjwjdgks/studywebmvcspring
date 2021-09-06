package me.seo.studywebmvc.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
// CLASS 레벨도 가능 @RequestMapping(method = RequestMethod.GET)
public class SimpleController {

    @ResponseBody
    // uri 확장자 mapping /seo 이렇게 mapping 하면 spring mvc가 암묵적으로 /seo.*로 해준다 .xml , .json에도 mapping 가능 but spring boot는 기본적으로 사용하지 않음
    // GET만 허용
    // ? 1개 이상
    // * 0개 이상 path 1개
    // ** 0개 이상 여러 path
    @GetMapping("/test/t*") //  = @RequestMapping(value = "/hellos", method = RequestMethod.GET)
    public String hellos(){
        return "hellos1";
    }

    @ResponseBody
    @GetMapping("/hellos/{name:[a-z]+}")
    public String helloname(@PathVariable String name){
         return "hello " + name;
    }

    //ex json만 보내는 요청 처리
    // MediaType.~VALUE -> return String, MediaType.~ -> return MediaType
    @RequestMapping(
            value = "/helloseo",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String helloSeo(){
         return "hello";
    }

    // 헤더와 파라미터 맵핑
    @ResponseBody
    @RequestMapping(value = "/helloheader",headers = HttpHeaders.FROM)  // HttpHeaders.FROM이 header에 있는 경우에만 처리
    // not도 가능
    //@RequestMapping(value = "/helloheader",headers = "!"+HttpHeaders.FROM)
    // 정확하게 일치하는 경우 (localhost와 일치)
    //@RequestMapping(value = "/helloheader",headers = HttpHeaders.FROM + "=" + "localhost")
    public String helloheader(){
        return "hellos";
    }


    @ResponseBody
    @RequestMapping(value = "/helloparam",params = "name") // name이라는 파라매터가 있어야 한다
    //@RequestMapping(value = "/helloparam",params = "name=test")  // name이라는 파라매터가 test와 일치했을 경우에만 처리
    public String helloparam(){
        return "hellos";
    }

    @ResponseBody
    @RequestMapping(value="headoroption")
    public String testhello(){
        return "hello head or option";
    }

    // 커스텀 애노테이션 // "/hellomapping 에 연결
    @GetHelloMapping
    public String hellomapping(){
        return "mapping";
    }
}
