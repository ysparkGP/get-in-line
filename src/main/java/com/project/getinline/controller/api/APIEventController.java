package com.project.getinline.controller.api;

import com.project.getinline.constant.EventStatus;
import com.project.getinline.dto.APIDataResponse;
import com.project.getinline.dto.EventRequest;
import com.project.getinline.dto.EventResponse;
import com.project.getinline.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

//@Validated
@RequiredArgsConstructor
//@RequestMapping("/api")
//@RestController
public class APIEventController {

    private final EventService eventService;

    @GetMapping("/events")
    public APIDataResponse<List<EventResponse>> getEvents(
            @Positive Long placeId,
            @Size(min = 2) String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime
    ){
        List<EventResponse> response = eventService
                .getEvents(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime)
                .stream().map(EventResponse::from).toList();

        return APIDataResponse.of(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    public APIDataResponse<String> createEvent(@Valid @RequestBody EventRequest eventRequest){
        //log.debug("보고 싶은 값: {}", eventRequest);
        boolean result = eventService.createEvent(eventRequest.toDTO());

        return APIDataResponse.of(Boolean.toString(result));
    }

    @GetMapping("/events/{eventId}")
    public APIDataResponse<EventResponse> getEvent(@PathVariable Long eventId){
        EventResponse eventResponse = EventResponse.from(eventService.getEvent(eventId)
                .orElse(null));

        return APIDataResponse.of(eventResponse);
    }

    @PostMapping("/events/{eventId}")
    public Boolean modifyEvent(@PathVariable Integer eventId){
        return true;
    }

    @DeleteMapping("/events/{eventId}")
    public Boolean removeEvent(@PathVariable Integer eventId){
        return true;
    }
}
