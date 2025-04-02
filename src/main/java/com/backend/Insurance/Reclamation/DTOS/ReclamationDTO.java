package com.backend.Insurance.Reclamation.DTOS;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ReclamationDTO {
    private String userEmail;
    private String typeReclamation;
    private String description;
    private String status;
}
