package com.backend.Insurance.User.UserService;

import com.backend.Insurance.Authnetification.Keycloak.AuthService;
import com.backend.Insurance.User.User;
import com.backend.Insurance.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthService authService;
    public ResponseEntity<String> syncUsers(List<UserRepresentation> userList) {
        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User list is empty");
        }
        for (UserRepresentation user : userList) {
            Optional<User> optUser = userRepository.findByCIN(user.getId());
            if (optUser.isPresent()) {
                continue;
            }
            User foundUser = User.builder()
                    .CIN(user.getId())
                    .email(user.getEmail())
                    .firstname(user.getFirstName())
                    .lastname(user.getLastName())
                    .username(user.getUsername())
                    .role("client_user") //Users will have by default client_user Role
                    .build();

            try {
                Integer statusCode = authService.register(user);
                if (statusCode == 201) {
                    // if user is added to keycloak then added to DB
                    userRepository.save(foundUser);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to register user in Keycloak");
                }
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error syncing users: " + e.getMessage());
            }
        }
        return ResponseEntity.ok("Users synchronized successfully");
    }

}
