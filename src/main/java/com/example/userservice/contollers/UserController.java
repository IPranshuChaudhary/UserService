package com.example.userservice.contollers;

import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.IncorrectDetailsEnteredException;
import com.example.userservice.exceptions.UserAlreadyExistsException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.Address;
import com.example.userservice.models.Name;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    public User getUserFromDto(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setPhone(userDto.getPhone());
        user.setId(userDto.getId());

        List<Address> addressList = userDto.getAddress();
        List<Address> addresses = new ArrayList<>();

        for (Address dtoAddress : addressList) {

            Address address = new Address();
            address.setCity(dtoAddress.getCity());
            address.setZip(dtoAddress.getZip());
            address.setStreet(dtoAddress.getStreet());
            addresses.add(address);
        }

        user.setAddress(addresses);

        Name name = new Name();
        name.setFirstname(userDto.getName().getFirstname());
        name.setLastname(userDto.getName().getLastname());

        user.setName(name);
        return user;
    }
    public UserDto getUserDtoFromUser(User savedUser){
        UserDto userDtoResponse = new UserDto();
        userDtoResponse.setAddress(savedUser.getAddress());
        userDtoResponse.setName(savedUser.getName());
        userDtoResponse.setPhone(savedUser.getPhone());
        userDtoResponse.setEmail(savedUser.getEmail());
        userDtoResponse.setUsername(savedUser.getUsername());
        userDtoResponse.setId(savedUser.getId());
        userDtoResponse.setPassword(savedUser.getPassword());
        return userDtoResponse;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable("id") Long id){
        try {
            User user = userServices.getSingleUser(id);

            UserDto userDto = getUserDtoFromUser(user);
            return new ResponseEntity<>(userDto, HttpStatus.OK);

        }catch (RuntimeException | UserNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){

        List<User> users = userServices.getAllUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user: users){
            userDtos.add(getUserDtoFromUser(user));
        }

        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userServices.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id,
                                              @RequestBody UserDto userDto){

        User user = getUserFromDto(userDto);

        UserDto userDto1 = getUserDtoFromUser(userServices.updateUser(id, user));
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody UserDto userDto){
        try {
            User user = getUserFromDto(userDto);
            Token token = userServices.userLogin(user);
            return new ResponseEntity<>(token.getValue(), HttpStatus.OK);

        }catch (IncorrectDetailsEnteredException | RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> userSignup(@RequestBody UserDto userDto){
        try {
            User user = getUserFromDto(userDto);
            User user1 = userServices.userSignUp(user);

            return new ResponseEntity<>(user1, HttpStatus.OK);

        }catch (UserAlreadyExistsException | RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody UserDto userDto){
        try {
            User user = getUserFromDto(userDto);
            userServices.logout(user);

            return new ResponseEntity<>( HttpStatus.OK);

        }catch (RuntimeException | UserNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
