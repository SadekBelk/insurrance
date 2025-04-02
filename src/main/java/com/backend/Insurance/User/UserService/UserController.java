package com.backend.Insurance.User.UserService;

import com.backend.Insurance.User.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userservice;
    @PutMapping("/synchronize")
    public ResponseEntity<String> SyncUsers (@RequestBody List<UserRepresentation> userList){
        return userservice.syncUsers(userList);
    }
}
