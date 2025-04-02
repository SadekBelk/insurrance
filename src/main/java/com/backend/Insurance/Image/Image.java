package com.backend.Insurance.Image;

import com.backend.Insurance.Reclamation.Reclamation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private String imageUrl;

    @ManyToOne
    @JsonBackReference
    private Reclamation reclamation;

}
