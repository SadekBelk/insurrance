package com.backend.Insurance.Reclamation;

import com.backend.Insurance.Image.Image;
import com.backend.Insurance.Reclamation.ENUMS.Status;
import com.backend.Insurance.Reclamation.ENUMS.TypeReclamation;
import com.backend.Insurance.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reclamation {
    @Id
    @GeneratedValue
    private long id;
    @Enumerated(EnumType.STRING)
    private TypeReclamation typeReclamation;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String description;
    private LocalDateTime date;
    @OneToMany
    @JsonManagedReference
    private List<Image> images;

    @ManyToOne
    @JsonBackReference
    private User user;
}
