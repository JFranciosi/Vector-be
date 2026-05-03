package com.vector.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.elytron.security.common.BcryptUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(nullable = false, unique = true)
    public String username;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String surname;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String password;

    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String clearPassword) {
        if (clearPassword != null && !clearPassword.startsWith("$2")) {
            this.password = BcryptUtil.bcryptHash(clearPassword);
        } else {
            this.password = clearPassword;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
