package com.example.userservice.repositories;

import com.example.userservice.models.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NameRepository extends JpaRepository<Name, Long> {
    public Optional<Name> findByFirstnameAndLastname(String firstname, String lastname);
}
