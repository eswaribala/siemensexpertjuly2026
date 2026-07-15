package com.boa.userservice.exceptions;

import com.boa.userservice.dtos.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundExcepion.class)
    public ResponseEntity<GenericResponse> userNotFoundExcepionHandler(UserNotFoundExcepion userNotFoundExcepion) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse(userNotFoundExcepion.getMessage()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<GenericResponse> roleNotFoundExcepionHandler(RoleNotFoundException roleNotFoundExcepion) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse(roleNotFoundExcepion.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericResponse> runTimeExceptioneHandler(RuntimeException runtimeException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(runtimeException.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> runTimeExceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(exception.getMessage()));
    }

}
