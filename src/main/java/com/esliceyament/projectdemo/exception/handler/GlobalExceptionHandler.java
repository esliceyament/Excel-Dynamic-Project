package com.esliceyament.projectdemo.exception.handler;
import com.esliceyament.projectdemo.dto.ExceptionDto;
import com.esliceyament.projectdemo.exception.ExistingClient;
import com.esliceyament.projectdemo.exception.NoNewProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoNewProperty.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handleNoNewProperty(NoNewProperty noNewProperty) {
        return new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), noNewProperty.getMessage());
    }

    @ExceptionHandler(ExistingClient.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleExistingClient(ExistingClient existingClient) {
        return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), existingClient.getMessage());
    }
}