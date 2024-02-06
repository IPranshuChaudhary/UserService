package com.example.userservice.repositories;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Override
    public Token save(Token token);

    @Transactional
    public void deleteAllByUser(User user);
}
