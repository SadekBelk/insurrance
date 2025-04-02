package com.backend.Insurance.User;

import com.backend.Insurance.Reclamation.Reclamation;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String CIN;
    private String username;
    @Column(unique = true)
    private String email;
    private String firstname;
    private String lastname;
    private String role;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Reclamation> reclamation;

    @Override
    public String toString() {
        return "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'';
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }

    public GrantedAuthority getGrantedAuthority() {
        return new SimpleGrantedAuthority(role);
    }
}
