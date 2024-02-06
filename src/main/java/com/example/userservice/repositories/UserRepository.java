package com.example.userservice.repositories;

import com.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User save(User user);

    public Optional<User> getUserById(Long id);

    @Query(value = "select * from User", nativeQuery = true)
    public List<User> getAllUser();

    public void deleteById(Long id);

//    public User merge(User user);

    public boolean existsByUsernameAndPassword(String userName, String password);

    public boolean existsByUsername(String userName);

    public Optional<User> getUserByUsername(String userName);
}
