package com.intern.asset;

import com.intern.asset.authentication.UserAlreadyExistException;
import com.intern.asset.detect.DetectException;
import com.intern.asset.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(DetectException.class)
    public final ResponseEntity<ErrorResponse> handleException(DetectException e) {
        return new ResponseEntity<>(new ErrorResponse(
                e.getMessage(),
                "detect_error"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<ErrorResponse> handleException(UserAlreadyExistException e) {
        return new ResponseEntity<>(new ErrorResponse(
                e.getMessage(),
                "register_error"),
                HttpStatus.BAD_REQUEST);
    }
}
