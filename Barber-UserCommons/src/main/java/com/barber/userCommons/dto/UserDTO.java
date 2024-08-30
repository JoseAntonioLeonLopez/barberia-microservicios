package com.barber.userCommons.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    
    private Long id;
    private String email;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String phoneNumber;
    private Long roleId;
}

