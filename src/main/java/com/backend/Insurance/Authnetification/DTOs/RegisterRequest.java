package com.backend.Insurance.Authnetification.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {
    private String Name;
    private String lastname;
    private String username;
    private String Email;
    private String Password;
}
