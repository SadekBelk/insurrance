package com.backend.Insurance.Authnetification.Config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;

public class KeycloakConfig {
    static Keycloak keycloak = null;
    static String serverUrl = "http://localhost:8080/";
    static String realm = "InsuranceApp";
     static String clientId = "Insurance";
     static String userName = "InsuranceAdmin";
     static String password = "InsuranceAdmin" ;
    public KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){

            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType("password")
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .build();
        }
        return keycloak;
    }
}