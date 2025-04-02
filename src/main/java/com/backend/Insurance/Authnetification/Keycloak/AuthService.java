package com.backend.Insurance.Authnetification.Keycloak;

import com.backend.Insurance.Authnetification.Config.KeycloakConfig;
import com.backend.Insurance.Authnetification.DTOs.LoginRequest;
import com.backend.Insurance.Authnetification.Security.JwtParser;
import com.backend.Insurance.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${keycloak.server.url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    private final JwtParser jwtParser;
    private final UserRepository userRepository;
    public ResponseEntity<String> login(LoginRequest loginDto) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/realms/InsuranceApp/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", clientId);
        requestBody.add("username", loginDto.getUsername());
        requestBody.add("password", loginDto.getPassword());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            // Handle 401, 403, and other 4xx errors
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
            } else if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to access this resource.");
            }
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle any other errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    public ResponseEntity<String> refresh(Map<String , String> refreshToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/realms/InsuranceApp/protocol/openid-connect/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("grant_type", "refresh_token");
            requestBody.add("client_id", "Insurance");
            requestBody.add("refresh_token", refreshToken.get("refreshToken"));
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    public Integer register(UserRepresentation userRep) {
        try {
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userRep.getUsername());
            user.setFirstName(userRep.getFirstName());
            user.setLastName(userRep.getLastName());
            user.setEmail(userRep.getEmail());
            user.setCredentials(userRep.getCredentials());
            user.setEnabled(true);
            user.setEmailVerified(true);
            UsersResource instance = KeycloakConfig.getInstance().realm(realm).users();
            Response response = instance.create(user);
            if (response.getStatus() == 201) {
                return response.getStatus();
            } else {
                throw new RuntimeException("Failed to create user: HTTP Status : " + response.getStatus());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while registering user", e);
        }
    }


}