package com.IndieAn.GoFundIndie.Common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@ControllerAdvice
@RestController
public class MethodArgumentNotValidExceptionHandler {

    // NotBlank Handler : 4006
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        try {
            for (FieldError fieldError : bindingResult.getFieldErrors()){
                if(fieldError == null) break;
                log.info(fieldError.getField() + "is Blank");
            }
            return ResponseEntity.status(400).body(new HashMap<>(){{
                put("code", 4006);
            }});
        } catch (NullPointerException e) {
            return ResponseEntity.status(400).body(new HashMap<>(){{
                put("code", 4006);
            }});
        }
    }
}
