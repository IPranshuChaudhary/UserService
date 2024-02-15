package com.example.userservice.repository;

import com.example.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    public Optional<Token> findByValue(String value);
    public Optional<Token> findByValueAndDeletedEquals(String token, boolean isDeleted);
    @Override
    void delete(Token entity);

    public Token save(Token token);
}
