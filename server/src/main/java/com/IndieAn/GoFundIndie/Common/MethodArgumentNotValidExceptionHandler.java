package com.IndieAn.GoFundIndie.Common;

import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class MethodArgumentNotValidExceptionHandler {

    // NotBlank Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        try {
            StringBuilder builder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                builder.append(fieldError.getField());
                builder.append(" ");
                builder.append(fieldError.getDefaultMessage());
                builder.append(" ");
                builder.append(fieldError.getRejectedValue());
                builder.append(" ");
            }
            return builder.toString();
        } catch (NullPointerException e) {
            return "value can not be a null";
        }
    }
}
