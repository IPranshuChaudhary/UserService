package com.example.userservice.repositories;

import com.example.userservice.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    public Optional<Address> findAddressByCity(String city);
}
