package com.example.Unilink.service;

import com.example.Unilink.Modelo.User;
import com.example.Unilink.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Autenticación
    public Optional<User> autenticarUsuario(String email, String contrasena) {
        return userRepository.findByEmailAndContrasena(email, contrasena);
    }

    // Registro
    public String registrarUsuario(User user) {
        Optional<User> existente = userRepository.findByEmail(user.getEmail());
        if (existente.isPresent()) {
            return "El correo ya está registrado.";
        }

        user.setFecha_registro(LocalDateTime.now());
        userRepository.save(user);
        return "Usuario registrado exitosamente.";
    }

    // Obtener usuario por ID
    public Optional<User> obtenerUsuarioPorId(Integer id) {
        return userRepository.findById(id);
    }
}
