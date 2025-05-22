package com.example.Unilink.interfaces;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Unilink.Modelo.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailAndContrasena(String email, String contrasena);
    Optional<User> findByEmail(String email);
}