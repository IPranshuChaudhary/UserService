package com.example.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncorrectDetailsEnteredException extends Exception{
    private String errorMessage;

    public IncorrectDetailsEnteredException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
