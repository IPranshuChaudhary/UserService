package com.example.userservice.controllerAdvices;

import com.example.userservice.dtos.ExceptionDto;
import com.example.userservice.exceptions.IncorrectDetailsEnteredException;
import com.example.userservice.exceptions.UserAlreadyExistsException;
import com.example.userservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handlesExceptionByGetSingleUser(
            UserNotFoundException userNotFoundException
    ){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorDto(userNotFoundException.getErrorMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handlesUserLoginException(
            IncorrectDetailsEnteredException incorrectDetailsEnteredException
    ){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorDto(incorrectDetailsEnteredException.getErrorMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> userSignUpException(
            UserAlreadyExistsException userAlreadyExistsException
    ){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErrorDto(userAlreadyExistsException.getErrorMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.FORBIDDEN);
    }
}
