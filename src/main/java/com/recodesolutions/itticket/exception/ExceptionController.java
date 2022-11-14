package com.recodesolutions.itticket.exception;

import com.recodesolutions.itticket.constants.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ReITException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(ReITException exception) {

        ErrorResponse errorResponse = new ErrorResponse(ApplicationConstants.FAILURE, exception.getCode(),exception.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}