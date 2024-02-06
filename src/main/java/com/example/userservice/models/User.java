package com.example.userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{

    private String email;
    private String username;
    private String password;

    @Cascade(CascadeType.PERSIST)
    @ManyToOne
    private Name name;

    @Cascade(CascadeType.PERSIST)
    @OneToMany()
    private List<Address> address;
    private String phone;

    @ManyToMany
    private List<Roles> roles;
    private boolean isEmailVerified;

}
