package com.backend.Insurance.Authnetification.DTOs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
