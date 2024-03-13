package com.example.userservice.services;

import com.example.userservice.dtos.SendEmailDto;
import com.example.userservice.exceptions.InvalidPasswordException;
import com.example.userservice.exceptions.InvalidTokenException;
import com.example.userservice.exceptions.InvalidUserException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repository.TokenRepository;
import com.example.userservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private static BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       TokenRepository tokenRepository,
                       KafkaTemplate<String, String> kafkaTemplate,
                       ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public User signUp(String name, String email, String password) throws JsonProcessingException {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User user1 = userRepository.save(user);

        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setTo(user1.getEmail());
        sendEmailDto.setFrom("n04356327@gmail.com");
        sendEmailDto.setMessage("Welcome aboard "+" "+user1.getName()+"!"

                +" We're thrilled to have you join our team");
        sendEmailDto.setSubject("Welcome Email");

        kafkaTemplate.send("SendEmail", objectMapper.writeValueAsString(sendEmailDto));
        return user1;
    }

    public Token login(String email, String password) throws InvalidUserException, InvalidPasswordException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()){
            throw new InvalidUserException();
        }

        User savedUser = optionalUser.get();

        if(!validatePassword(password, savedUser.getHashedPassword())){
            throw new InvalidPasswordException();
        }

        Token token = getToken(savedUser);
        tokenRepository.save(token);

        return token;
    }

    public void logout(String token) throws InvalidTokenException {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedEquals(token, false);

        if (optionalToken.isEmpty()){
            throw new InvalidTokenException();
        }

        Token token1 = optionalToken.get();
        token1.setDeleted(true);

        tokenRepository.save(token1);
    }

    public User validateToken(String tokenVal) throws InvalidTokenException {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedEquals(tokenVal,false);
        if (!optionalToken.isPresent()){
            throw new  InvalidTokenException();
        }
        Token token1 = optionalToken.get();
        LocalDate currentDate = LocalDate.now();

        // Convert LocalDate to Date
        Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (token1.getExpiryAt().before(date)) {
            throw new InvalidTokenException();
        }

        User user = optionalToken.get().getUser();

        return user;
    }

    public static boolean validatePassword(String rawPassword, String hashedPassword){
        if (!bCryptPasswordEncoder.matches(rawPassword, hashedPassword)){
            return false;
        }
        return true;
    }

    public static Token getToken(User user){
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);

        Date date = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(date);
        token.setValue(RandomStringUtils.randomAlphabetic(128));
        return token;
    }
}
