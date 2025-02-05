package com.appsdeveloperblog.reactive.ws.users.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {

    @NotBlank(message = "First name should no be empty")
    @Size(min = 2, max = 50, message = "First name should no be shorter than 2 characters and greater than 50 characters" )
    private String firstName;

    @NotBlank(message = "Last name should no be empty")
    @Size(min = 2, max = 50, message = "Last name should no be shorter than 2 characters and greater than 50 characters" )
    private String lastName;

    @NotBlank(message = "Email should no be empty")
    @Email(message = "Please enter a valid email" )
    private String email;

    @NotBlank(message = "Password should no be empty")
    @Size(min = 8, max = 16, message = "Password should no be shorter than 8 characters and greater than 16 characters" )
    private String password;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
