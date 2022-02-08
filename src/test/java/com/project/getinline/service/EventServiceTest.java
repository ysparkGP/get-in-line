package com.project.getinline.service;

import com.project.getinline.constant.ErrorCode;
import com.project.getinline.constant.EventStatus;
import com.project.getinline.dto.EventDTO;
import com.project.getinline.exception.GeneralException;
import com.project.getinline.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    //@Autowired
    @InjectMocks private EventService sut; // 목 주입 객체
    @Mock private EventRepository eventRepository; // 목 생성 객체


//    @BeforeEach
//    void setUp(){
//        sut = new EventService();
//    }

    @DisplayName("검색 조건 없이 이벤트 검색하면 전체 결과 출력")
    @Test
    void givenNothing_whenSearchingEvents_thenReturnsEntireEventList(){
        // Given
        given(eventRepository.findEvents(null,null,null,null,null))
                .willReturn(List.of(
                        createEventDTO(1L,"스프링 공부", true),
                        createEventDTO(1L,"리액트 공부", false)
                ));

        // When
        List<EventDTO> list = sut.getEvents(null,null,null,null,null);

        // Then
        assertThat(list).hasSize(2);
        //then(eventRepository).should().findEvents(null,null,null,null,null);
        Mockito.verify(eventRepository).findEvents(null,null,null,null,null);
    }

    @DisplayName("검색 조건과 함께 이벤트 검색하면 검색된 결과 출력")
    @Test
    void givenSearchParams_whenSearchingEvents_thenReturnsEventList(){
        // Given
        Long placeId = 1L;
        String eventName = "스프링 공부";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDatetime = LocalDateTime.of(2022,2,4,0,0,0);
        LocalDateTime eventEndDatetime = LocalDateTime.of(2022,2,5,0,0,0);

        given(eventRepository.findEvents(1L,"스프링 공부",eventStatus,eventStartDatetime,eventEndDatetime))
                .willReturn(List.of(createEventDTO(1L,"스프링 공부",eventStatus,eventStartDatetime,eventEndDatetime)));

        // When
        List<EventDTO> list = sut.getEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);

        // Then
        assertThat(list)
                .hasSize(1)
                .allSatisfy(event -> {
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("placeId", placeId)
                            .hasFieldOrPropertyWithValue("eventName", eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus );

                    //범위 검색
                    assertThat(event.eventStartDatetime()).isAfterOrEqualTo(eventStartDatetime);
                    assertThat(event.eventStartDatetime()).isBeforeOrEqualTo(eventStartDatetime);
                });

        Mockito.verify(eventRepository).findEvents(placeId,eventName,eventStatus,eventStartDatetime,eventEndDatetime);
    }

    @DisplayName("이벤트 검색하면 에러 발생하는 경우, 줄서기 프로젝트 기본 에러로 전환하여 예외 던짐")
    @Test
    void givenDataRelatedException_whenSearchingEvents_thenThrowsGeneralException(){
        // Given
        RuntimeException e = new RuntimeException("This is test.");
        given(eventRepository.findEvents(any(), any(), any(), any(), any()))
                .willThrow(e);

        // When
        Throwable thrown = catchThrowable(() ->sut.getEvents(null,null,null,null,null));

        // Then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        //then(eventRepository).should().findEvents(null,null,null,null,null);
        Mockito.verify(eventRepository).findEvents(any(), any(), any(), any(), any());
    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면 해당 이벤트 정보를 출력")
    @Test
    void givenEventId_whenSearchingExistingEvent_thenReturnsEvent(){
        // Given
        Long eventId = 1L;
        EventDTO eventDTO = createEventDTO(1L, "스프링 공부", true);
        given(eventRepository.findEvent(eventId)).willReturn(Optional.of(eventDTO));

        // When
        Optional<EventDTO> result = sut.getEvent(eventId);

        // Then
        assertThat(result).hasValue(eventDTO);
        Mockito.verify(eventRepository).findEvent(eventId);
    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면 빈 정보를 출력")
    @Test
    void givenEventId_whenSearchingNoneExistingEvent_thenReturnsEmptyOptional(){
        // Given
        Long eventId = 2L;
        given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());

        // When
        Optional<EventDTO> result = sut.getEvent(eventId);

        // Then
        assertThat(result).isEmpty();
        Mockito.verify(eventRepository).findEvent(eventId);
    }

    @DisplayName("이벤트 정보를 주면, 이벤트를 생성하고 결과를 true 로 출력")
    @Test
    void givenEvent_whenCreating_thenCreatesEventAndReturnsTrue(){
        // Given
        EventDTO dto = createEventDTO(1L, "스프링 공부", false);
        given(eventRepository.insertEvent(dto)).willReturn(true);

        // When
        boolean result = sut.createEvent(dto);

        // Then
        assertThat(result).isTrue();
        verify(eventRepository).insertEvent(dto);
    }

    @DisplayName("이벤트 정보를 주지 않으면, 생성을 중단하고 결과를 false 로 출력")
    @Test
    void givenNothing_whenCreating_thenAbortCreatingAndReturnsFalse(){
        // Given
        given(eventRepository.insertEvent(null)).willReturn(false);

        // When
        boolean result = sut.createEvent(null);

        // Then
        assertThat(result).isFalse();
        verify(eventRepository).insertEvent(null);
    }

    @DisplayName("이벤트 ID와 정보를 주면 이벤트 정보를 변경하고 결과는 true 출력")
    @Test
    void givenEventIdAndItsInfo_whenModifying_thenModifiesEventAndReturnsTrue(){
        // Given
        Long eventId = 1L;
        EventDTO dto = createEventDTO(1L, "리액트 공부", false);
        given(eventRepository.updateEvent(eventId, dto)).willReturn(true);

        // When
        boolean result = sut.modifyEvent(eventId, dto);

        // Then
        assertThat(result).isTrue();
        verify(eventRepository).updateEvent(eventId,dto);
    }

    @DisplayName("이벤트 ID와 정보를 주지 않으면 이벤트 정보를 변경 중단하고 결과는 false 출력")
    @Test
    void givenNoEventId_whenModifying_thenAbortModifyingAndReturnsFalse(){
        // Given
        EventDTO dto = createEventDTO(1L, "리액트 공부", false);
        given(eventRepository.updateEvent(null, dto)).willReturn(false);

        // When
        boolean result = sut.modifyEvent(null, dto);

        // Then
        assertThat(result).isFalse();
        verify(eventRepository).updateEvent(null,dto);
    }

    @DisplayName("이벤트 ID만 주고 변경할 정보를 주지 않으면 이벤트 정보를 변경 중단하고 결과는 false 출력")
    @Test
    void givenEventIdOnly_whenModifying_thenAbortModifyingEventAndReturnsFalse(){
        // Given
        Long eventId = 1L;
        given(eventRepository.updateEvent(eventId, null)).willReturn(false);

        // When
        boolean result = sut.modifyEvent(eventId, null);

        // Then
        assertThat(result).isFalse();
        verify(eventRepository).updateEvent(eventId, null);
    }

    @DisplayName("이벤트 ID를 주면 이벤트 정보를 삭제하고 결과를 true 출력")
    @Test
    void givenEventId_whenDeleting_thenDeletesEventAndReturnsTrue(){
        // Given
        Long eventId = 1L;
        given(eventRepository.deleteEvent(eventId)).willReturn(true);

        // When
        boolean result = sut.removeEvent(eventId);

        // Then
        assertThat(result).isTrue();
        verify(eventRepository).deleteEvent(eventId);
    }

    @DisplayName("이벤트 ID를 주지 않으면 삭제 중단하고 결과를 false 출력")
    @Test
    void givenNothing_whenDeleting_thenAbortDeletingEventAndReturnsFalse(){
        // Given
        given(eventRepository.deleteEvent(null)).willReturn(false);

        // When
        boolean result = sut.removeEvent(null);

        // Then
        assertThat(result).isFalse();
        verify(eventRepository).deleteEvent(null);
    }

    private EventDTO createEventDTO(long placeId, String eventName, boolean isMorning){
        String hourStart = isMorning? "09": "13";
        String hourEnd = isMorning? "12": "18";

        return createEventDTO(
                placeId,
                eventName,
                EventStatus.OPENED,
                LocalDateTime.parse("2022-02-05T%s:00:00".formatted(hourStart)),
                LocalDateTime.parse("2022-02-05T%s:00:00".formatted(hourEnd))
        );
    }

    private EventDTO createEventDTO(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ){
        return EventDTO.of(
          placeId,
          eventName,
          eventStatus,
          eventStartDatetime,
          eventEndDatetime,
          0,
          2400,
          "마스크는 필수 착용",
          LocalDateTime.now(),
          LocalDateTime.now()
        );
    }
}