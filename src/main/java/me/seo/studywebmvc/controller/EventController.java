package me.seo.studywebmvc.controller;

import me.seo.studywebmvc.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// spring boot 4.3 이상 추천
@Controller // web mvc controller 역할
public class EventController {

    @Autowired
    EventService eventService;

    // 특정 요청이 들어 올때
    // get으로 events 들어올때 처리
    //@RequestMapping(value = "/events", method = RequestMethod.GET)
    @GetMapping("/events")
    // Model map 처럼 key value
    // return 문자열이 view를 찾는데 keyword
    public String events(Model model){
        model.addAttribute("events",eventService.getEvents());
        return "events";

    }
}
