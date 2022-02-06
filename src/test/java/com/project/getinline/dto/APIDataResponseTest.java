package com.project.getinline.dto;

import com.project.getinline.constant.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class APIDataResponseTest {

    @DisplayName("문자열 데이터가 주어지면, 표준 성공 응답을 생성")
    @Test
    void givenStringData_whenCreatingResponse_thenReturnsSuccessfulResponse(){
        // Given
        String data = "test data";

        // When
        APIDataResponse response = APIDataResponse.of(data);

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", data);

    }

    @DisplayName("데이터가 없을 때, 비어있는 표준 성공 응답을 생성")
    @Test
    void givenNothing_whenCreatingResponse_thenReturnsEmptySuccessfulResponse(){
        // Given

        // When
        APIDataResponse response = APIDataResponse.empty();

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", null);

    }
}