package com.vector.dto.response.auth;

public class AuthResponse {
    public String token;
    public String username;
    public String name;
    public String surname;

    public AuthResponse(String token, String username, String name, String surname) {
        this.token = token;
        this.username = username;
        this.name = name;
        this.surname = surname;
    }
}
