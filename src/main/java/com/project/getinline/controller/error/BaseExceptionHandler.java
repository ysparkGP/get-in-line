package com.project.getinline.controller.error;

import com.project.getinline.constant.ErrorCode;
import com.project.getinline.dto.APIErrorResponse;
import com.project.getinline.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice
public class BaseExceptionHandler {

    // 일반적인 예외 상황 처리
    @ExceptionHandler
    public ModelAndView general(GeneralException e, HttpServletResponse response){
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView("error",
                Map.of(
                        "statusCode", status.value(),
                        "errorCode", errorCode,
                        "message", errorCode.getMessage(e)
                ),
                status
        );
    }

    // general 에서 잡지 못한 예외 처리리
   @ExceptionHandler
    public ModelAndView exception(Exception e){
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView("error",
                Map.of(
                        "statusCode", status.value(),
                        "errorCode", errorCode,
                        "message", errorCode.getMessage(e)
                ),
                status
        );
    }
}
