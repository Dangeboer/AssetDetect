package com.intern.asset;

import com.intern.asset.function.ping.PingException;
import com.intern.asset.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(PingException.class)
    public final ResponseEntity<ErrorResponse> handleException(PingException e) {
        return new ResponseEntity<>(new ErrorResponse(
                e.getMessage(),
                "ping_check_request"),
                HttpStatus.BAD_REQUEST);
    }
}
