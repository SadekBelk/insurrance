package com.backend.Insurance.Authnetification.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LoginResponse {
    String email;
    Boolean isEmailVerified;
    String username;
    List<String> Role;
    String name;
    String lastName;
}
