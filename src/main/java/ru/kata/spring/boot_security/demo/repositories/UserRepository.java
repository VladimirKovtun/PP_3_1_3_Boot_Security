package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u join fetch u.roles where u.username = :name")
    Optional<User> findByUsername(String name);

    @Query("from User u join fetch u.roles where u.id = :id")
    Optional<User> findById(Long id);
}
