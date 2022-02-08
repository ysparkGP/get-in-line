package com.project.getinline.repository;

import com.project.getinline.constant.EventStatus;
import com.project.getinline.dto.EventDTO;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


// TODO: 인스턴스 생성 편의를 위해 임시로 default 사용. repository layer 구현 완성 되면 삭제
public interface EventRepository {
    default List<EventDTO> findEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ){
        return List.of();
    }

    default Optional<EventDTO> findEvent(Long eventId) {
        return Optional.empty();
    }

    default boolean insertEvent(EventDTO eventDTO){
        return false;
    }

    default boolean updateEvent(Long eventId, EventDTO dto){
        return false;
    }

    default boolean deleteEvent(Long eventId){
        return false;
    }
}
