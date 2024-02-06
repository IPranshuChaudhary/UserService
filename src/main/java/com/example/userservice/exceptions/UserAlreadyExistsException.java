package com.example.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExistsException extends Exception{
    private String errorMessage;

    public UserAlreadyExistsException(String errorMessage) {

        this.errorMessage = errorMessage;
    }
}
