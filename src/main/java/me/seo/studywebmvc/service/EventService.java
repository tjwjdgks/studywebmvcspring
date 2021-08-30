package me.seo.studywebmvc.service;

import me.seo.studywebmvc.dto.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    public List<Event> getEvents(){
        Event event = Event.builder()
                .name("스프링")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2019,1,10,10,0))
                .endDateTime(LocalDateTime.of(2019,1,10,12,0))
                .build();

        Event event2 = Event.builder()
                .name("스프링 2차")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2019,1,17,10,0))
                .endDateTime(LocalDateTime.of(2019,1,17,12,0))
                .build();

        return List.of(event,event2);
    }
}
