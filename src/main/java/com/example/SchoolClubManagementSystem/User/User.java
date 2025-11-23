package com.example.SchoolClubManagementSystem.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Полето не може да бъде празно")
    @Size(min = 6, message = "Потребителското име трябва да съдържа поне 6 символа")
    @Size(max = 14, message = "Потребителското име не трябва да надвишава 14 символа")
    private String username;

    @NotEmpty(message = "Полето не може да бъде празно")
    @Size(min = 2, message = "Името трябва да съдържа поне 2 символа")
    private String firstName;

    @NotEmpty(message = "Полето не може да бъде празно")
    @Size(min = 2, message = "Фамилията трябва да съдържа поне 2 символа")
    private String lastName;

    @NotEmpty(message = "Полето не може да бъде празно.")
    @Size(min = 5, message = "Имейлът трябва да съдържа поне 5 символа")
    private String email;

    @NotEmpty(message = "Полето не може да бъде празно.")
    @Size(min = 8, message = "Паролата трябва да съдържа поне 8 символа")
    @Size(max = 1000, message = "Паролата не трябва да надвишава 14 символа")
    private String password;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'STUDENT'")
    private String role;
    @Column(columnDefinition = "BIT DEFAULT 1")
    private boolean enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}