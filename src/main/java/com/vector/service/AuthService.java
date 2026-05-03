package com.vector.service;

import com.vector.dto.request.login.LoginRequest;
import com.vector.dto.request.register.RegisterRequest;
import com.vector.dto.response.auth.AuthResponse;
import com.vector.model.User;
import com.vector.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@ApplicationScoped
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username).isPresent()) {
            throw new WebApplicationException("Username already exists", Response.Status.BAD_REQUEST);
        }
        if (userRepository.findByEmail(request.email).isPresent()) {
            throw new WebApplicationException("Email already exists", Response.Status.BAD_REQUEST);
        }

        User user = new User();
        user.username = request.username;
        user.email = request.email;
        user.name = request.name;
        user.surname = request.surname;
        user.setPassword(request.password); // Hashes the passwords

        userRepository.persist(user);

        String token = generateToken(user);
        return new AuthResponse(token, user.username, user.name, user.surname);
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.username);
        if (userOptional.isEmpty()) {
            throw new WebApplicationException("Invalid credentials", Response.Status.UNAUTHORIZED);
        }

        User user = userOptional.get();
        if (!BcryptUtil.matches(request.password, user.password)) {
            throw new WebApplicationException("Invalid credentials", Response.Status.UNAUTHORIZED);
        }

        String token = generateToken(user);
        return new AuthResponse(token, user.username, user.name, user.surname);
    }

    public void logout() {
    }


    private String generateToken(User user) {
        return Jwt.upn(user.username)
                .groups("User")
                .claim("email", user.email)
                .claim("name", user.name)
                .claim("surname", user.surname)
                .sign();
    }
}
