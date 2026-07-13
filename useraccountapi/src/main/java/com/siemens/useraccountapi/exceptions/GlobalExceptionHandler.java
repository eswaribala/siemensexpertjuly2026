package com.siemens.useraccountapi.exceptions;

import com.siemens.useraccountapi.dtos.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAccountNotFoundException.class)
    public ResponseEntity<GenericResponse> userAccountNotFoundExceptionHandler(UserAccountNotFoundException userAccountNotFoundException){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse
                (userAccountNotFoundException.getMessage()));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericResponse> runTimeExceptionHandler(RuntimeException runtimeException){

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse
                (runtimeException.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentInvalidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){

        Map<String,Object> responseMap=new HashMap<>();
        Map<String,String> errors=new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error->{

            String fieldName=((FieldError)error).getField();
            String errorMessage=error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });

        responseMap.put("timestamp", LocalDate.now().toString());

        responseMap.put("errors",errors);
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);

    }


}
