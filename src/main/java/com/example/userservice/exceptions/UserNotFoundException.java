package com.example.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends Throwable {
    private String errorMessage;

    public UserNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
