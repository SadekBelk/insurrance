package com.backend.Insurance.Reclamation.DTOS;
import com.backend.Insurance.Reclamation.Reclamation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReclamationMessageDto {
    private ReclamationDTO reclamation;
}
