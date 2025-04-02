package com.backend.Insurance.Authnetification.Security;

import com.backend.Insurance.Authnetification.DTOs.LoginResponse;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class JwtParser {
    LoginResponse loginResponse;
    @Value("${keycloak.resource}")
    private String clientId;

    public LoginResponse parseJwt(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            Map<String , Object> claims = claimsSet.getClaims();
            loginResponse = LoginResponse.builder()
                    .email((String) claims.get("email"))
                    .username((String) claims.get("preferred_username"))
                    .Role(extractRole(claims))
                    .isEmailVerified(Boolean.valueOf(("email_verified")))
                    .name( (String) claims.get("given_name"))
                    .lastName((String)claims.get("family_name"))
                    .build();

            return loginResponse;

        } catch (ParseException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
    public List<String> extractRole(Map<String, Object> claims) {
        Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
        if (resourceAccess != null && resourceAccess.containsKey(clientId)) {
            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(clientId);
            return (List<String>) clientAccess.get("roles");
        }
        return null;
    }
}
