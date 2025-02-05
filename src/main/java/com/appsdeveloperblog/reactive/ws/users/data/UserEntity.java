package com.appsdeveloperblog.reactive.ws.users.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;
/*
Spring Data R2DBC -> org.springframework.data.relational
JPA -> jakarta.persistence
* Feature       | Spring Data R2DBC     | JPA
Entity Mapping  | @Table, @Id, @Column	| @Entity, @Table, @Id, @Column
Relationships	| @MappedCollection	    | @OneToMany, @ManyToOne, @ManyToMany
GenerationType	| @Id, IDENTITY	        | @Id, GenerationType.IDENTITY, GenerationType.SEQUENCE
* */


@Table(name = "users") //this is a @Table annotation from jpa for reactive application (Spring Data R2DBC)
public class UserEntity {
    @Id
    private UUID id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}
