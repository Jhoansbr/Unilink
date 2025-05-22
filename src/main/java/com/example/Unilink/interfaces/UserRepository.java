package com.example.Unilink.interfaces;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Unilink.Modelo.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}