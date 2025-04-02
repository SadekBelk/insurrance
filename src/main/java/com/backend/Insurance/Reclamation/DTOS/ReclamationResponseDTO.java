package com.backend.Insurance.Reclamation.DTOS;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReclamationResponseDTO {
    private Long id;
    private String Email;
    private String fullName;
    private String Description;
    private String status;
    private String type;
    private LocalDateTime date;
    private List<String> imageUrl;
}
