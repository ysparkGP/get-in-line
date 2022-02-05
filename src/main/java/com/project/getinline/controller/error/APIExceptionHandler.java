package com.project.getinline.controller.error;

import com.project.getinline.constant.ErrorCode;
import com.project.getinline.dto.APIErrorResponse;
import com.project.getinline.exception.GeneralException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    // 일반적인 예외 상황 처리
    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request){
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        return super.handleExceptionInternal
                (e,
                APIErrorResponse.of(false,errorCode.getCode(), errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request);
    }

    // general 에서 잡지 못한 예외 처리
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request){
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return super.handleExceptionInternal
                (e,
                APIErrorResponse.of(false,errorCode.getCode(), errorCode.getMessage(e)),
                HttpHeaders.EMPTY,
                status,
                request);
    }

    // 스프링에서 발생한 에러 처리(ResponseEntityExceptionHandler 클래스를 상속받아
    // handleExceptionInternal 메서드를 원래 null 이었던 body 를 채워주고 호출)
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorCode errorCode = status.is4xxClientError() ?
                ErrorCode.SPRING_BAD_REQUEST :
                ErrorCode.SPRING_INTERNAL_ERROR;

        return super.handleExceptionInternal
                (ex,
                APIErrorResponse.of(false,errorCode.getCode(), errorCode.getMessage(ex)),
                headers,
                status,
                request);
    }
}
