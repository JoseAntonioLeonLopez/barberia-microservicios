package com.barber.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "First surname is mandatory")
    @Size(max = 100, message = "First surname cannot exceed 100 characters")
    private String firstSurname;

    @Size(max = 100, message = "Second surname cannot exceed 100 characters")
    private String secondSurname;

    @NotBlank(message = "Phone number is mandatory")
    @Size(max = 100, message = "Phone number cannot exceed 100 characters")
    private String phoneNumber;

    private Long roleId;
}

