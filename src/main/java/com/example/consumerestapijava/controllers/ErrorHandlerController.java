package com.example.consumerestapijava.controllers;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
@ResponseBody
public class ErrorHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(IllegalArgumentException exception) {
        return String.format("%s",exception.getMessage()) ;
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object>  handleRuntimeException(RuntimeException ex,WebRequest request) {
        return new ResponseEntity<Object>(
                ApiException.builder()
                        .message(ex.getMessage())
                        .path(((ServletWebRequest)request).getRequest().getRequestURI())
                        .detailMessage(ex.getLocalizedMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                ApiException.builder()
                        .message("Access denied message here")
                        .path(request.getContextPath())
                        .detailMessage(ex.getLocalizedMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build(),
                        new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @Data
    @Builder
   public static class ApiException{
        private String message;
        private String detailMessage;
        private String path;
        private String timestamp;
   }
}
