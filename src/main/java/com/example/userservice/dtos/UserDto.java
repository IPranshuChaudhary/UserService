package com.example.userservice.dtos;

import com.example.userservice.models.Address;
import com.example.userservice.models.Name;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private List<Address> address;
    private String phone;

}
