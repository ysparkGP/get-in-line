package com.project.getinline.integration;

import com.project.getinline.constant.ErrorCode;
import com.project.getinline.constant.EventStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class APIEventIntegrationTest {

    @Autowired private MockMvc mvc;

    @Test
    void test() throws Exception{
        // Given


        // When & Then
        mvc.perform(
                MockMvcRequestBuilders.get("/api/events")
                        .queryParam("placeId","1")
                        .queryParam("eventName","스프링 공부")
                        .queryParam("eventStatus", EventStatus.OPENED.name())
                        .queryParam("eventStartDatetime","2022-02-07T00:00:00")
                        .queryParam("eventEndDatetime","2022-02-08T00:00:00")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                .andDo(print());
    }

}