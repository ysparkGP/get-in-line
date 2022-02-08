package com.project.getinline.controller;

import com.project.getinline.constant.EventStatus;
import com.project.getinline.dto.EventResponse;
import com.project.getinline.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events")
public class EventController {

    @GetMapping()
    public ModelAndView events()
    {
        Map<String, Object> map = new HashMap<>();

        map.put("events", List.of(
                EventResponse.of(
                        1L,
                        "스프링 공부",
                        EventStatus.OPENED,
                        LocalDateTime.of(2022,2,7,0,0,0),
                        LocalDateTime.of(2022,2,8,0,0,0),
                        0,
                        24,
                        "마스크 필수 착용"),
                EventResponse.of(
                        2L,
                        "리액트 공부",
                        EventStatus.OPENED,
                        LocalDateTime.of(2022,2,7,0,0,0),
                        LocalDateTime.of(2022,2,8,0,0,0),
                        0,
                        24,
                        "마스크 필수 착용")
        ));

        return new ModelAndView("event/index", map);
    }

    @GetMapping("/{eventId}")
    public ModelAndView eventDetail(@PathVariable Integer eventId){
        Map<String, Object> map = new HashMap<>();

        map.put("events", List.of(
                EventResponse.of(
                        1L,
                        "스프링 공부",
                        EventStatus.OPENED,
                        LocalDateTime.of(2022,2,7,0,0,0),
                        LocalDateTime.of(2022,2,8,0,0,0),
                        0,
                        24,
                        "마스크 필수 착용")
        ));
        return new ModelAndView("event/detail", map);
    }
}
