package com.example.userservice.services;

import com.example.userservice.exceptions.IncorrectDetailsEnteredException;
import com.example.userservice.exceptions.UserAlreadyExistsException;
import com.example.userservice.exceptions.UserNotFoundException;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.AddressRepository;
import com.example.userservice.repositories.NameRepository;
import com.example.userservice.repositories.TokenRepository;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SelfUserServices implements UserServices{

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private NameRepository nameRepository;
    private TokenRepository tokenRepository;

    @Autowired
    public SelfUserServices(UserRepository userRepository,
                            AddressRepository addressRepository,
                            NameRepository nameRepository,
                            TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.nameRepository = nameRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User getSingleUser(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.getUserById(id);

        if (userOptional.isEmpty()){
            throw new UserNotFoundException("User with id: "+id+" " +
                    "not found");
        }

        return userOptional.get();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.getAllUser();
        return users;
    }

    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
//        return userSignUp(user);
//        return userRepository.merge(user);
        return null;
    }

    @Override
    public Token userLogin(User user) throws IncorrectDetailsEnteredException {
        if (userRepository.existsByUsernameAndPassword(user.getUsername(), user.getPassword())){
            Optional<User> user1 = userRepository.getUserByUsername(user.getUsername());
            return generateToken(user1.get());
        }
        throw new IncorrectDetailsEnteredException("Incorrect Login Details Entered");
    }

    @Override
    public void logout(User user) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.getUserByUsername(user.getUsername());
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("User doesn't exist");
        }
        tokenRepository.deleteAllByUser(userOptional.get());
    }

    @Override
    public User userSignUp(User user) throws UserAlreadyExistsException {

        boolean exists = userRepository.existsByUsername(
                user.getUsername());
        if (exists){
            throw new UserAlreadyExistsException("User with userName: "+user.getUsername()
            +" already exists");
        }

        return userRepository.save(user);
    }

    //used by userLogin
    public Token generateToken(User user) {
        String uuid = UUID.randomUUID().toString();
        String id = "uuid = " + uuid;
        Token token = new Token();
        token.setUser(user);
        token.setValue(id);
        return tokenRepository.save(token);
    }

}
