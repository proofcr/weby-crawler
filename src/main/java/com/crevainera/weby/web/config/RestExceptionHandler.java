package com.crevainera.weby.web.config;

import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.web.dto.ResponseErrorDTO;
import com.crevainera.weby.web.constant.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static com.crevainera.weby.web.constant.ErrorMessages.ENDPOINT_INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NullPointerException.class, IllegalArgumentException.class, WebyException.class})
    protected ResponseEntity<?> handleException(final RuntimeException runtimeException, final WebRequest request) {
        HttpServletRequest webRequest = ((ServletWebRequest) request).getRequest();
        HttpStatus status;
        String error;
        String message;
        if (runtimeException instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            error = ErrorMessages.ENDPOINT_VALIDATION_ERROR.getMessage();
            message = runtimeException.getMessage();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            error = ENDPOINT_INTERNAL_SERVER_ERROR.getMessage();
            message = ErrorMessages.ENDPOINT_WE_ARE_WORKING.getMessage();
        }
        ResponseErrorDTO exceptionDTO = new ResponseErrorDTO();
        exceptionDTO.setError(error);
        exceptionDTO.setMessage(message);
        exceptionDTO.setStatus(status.toString());

        return new ResponseEntity<>(exceptionDTO, status);
    }
}
