package com.backend.Insurance.User.UserService;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserSyncScheduler {

    @Autowired
    private UserService userService;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(cron = "0 0 * * * ?")
    public void syncUsersFromExternalApi() {
        String apiUrl = "http://localhost:8083/getUsers";
        try {
            ResponseEntity<UserRepresentation[]> response = restTemplate.getForEntity(apiUrl, UserRepresentation[].class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200) && response.getBody() != null) {
                List<UserRepresentation> userList = Arrays.asList(response.getBody());
                userService.syncUsers(userList);
            } else {
                System.out.println("Failed to fetch users, status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
    }
}
