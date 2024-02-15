package com.example.userservice.controllers;

import com.example.userservice.dtos.LoginRequestDto;
import com.example.userservice.dtos.LogoutRequestDto;
import com.example.userservice.dtos.SignUpRequestDto;
import com.example.userservice.exceptions.InvalidPasswordException;
import com.example.userservice.exceptions.InvalidTokenException;
import com.example.userservice.exceptions.InvalidUserException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto request){
        return userService.signUp(request.getName(), request.getEmail(), request.getPassword());
    }

    @PostMapping("/login")
    public String signUp(@RequestBody LoginRequestDto request) throws InvalidPasswordException, InvalidUserException {
        Token token = userService.login(request.getEmail(), request.getPassword());
        return token.getValue();
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequestDto request) throws InvalidTokenException {
        userService.logout(request.getToken());
    }

    @PostMapping("/validate/{token}")
    public User validateToken(@PathVariable("token") String token) throws InvalidTokenException {
        return userService.validateToken(token);
    }

}
