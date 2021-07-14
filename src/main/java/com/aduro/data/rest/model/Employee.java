package com.aduro.data.rest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    @NotEmpty(message = "Please provide full name")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(min = 4, max = 4, message = "Office must be in the range 100A - 599F")
    @Pattern(regexp = "^(?:[1-5][0-9][0-9][A-F])$", message = "Office must be in the range 100A - 599F")
    @NotEmpty(message = "Please provide office")
    @Column(nullable = false, length = 4)
    private String office;

    @Email(regexp = ".*@oscorp.com", message = "Must be a valid email with oscorp.com domain")
    @Size(max = 150, message = "Email cannot exceed 150 characters")
    @NotEmpty(message = "Please provide email address")
    @Column(nullable = false, length = 150)
    private String email;

    @Size(max = 12, message = "Must be a valid North American phone number with area code 415, in the format 415.456.7890")
    @Pattern(regexp = "(^415\\.\\d{3}\\.\\d{4})", message = "Must be a valid North American phone number with area code 415, in the format 415.456.7890")
    @NotEmpty(message = "Please provide telephone number")
    @Column(nullable = false, length = 12)
    private String phone;

    @Size(max = 150, message = "Role cannot exceed 150 characters")
    @NotEmpty(message = "Please provide role")
    @Column(nullable = false, length = 150)
    private String role;
}
