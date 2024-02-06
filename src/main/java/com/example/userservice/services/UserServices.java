package com.example.userservice.services;

import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.IncorrectDetailsEnteredException;
import com.example.userservice.exceptions.UserAlreadyExistsException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;

@Service
public interface UserServices {
    public User userSignUp(User user) throws UserAlreadyExistsException;

    public User getSingleUser(Long id) throws UserNotFoundException;

    public List<User> getAllUsers();

    public void deleteUser(Long id);

    public User updateUser(Long id, User user);

    public Token userLogin(User user) throws IncorrectDetailsEnteredException;

    public void logout(User user) throws UserNotFoundException;
}
