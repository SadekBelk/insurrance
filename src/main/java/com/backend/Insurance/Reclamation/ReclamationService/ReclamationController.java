package com.backend.Insurance.Reclamation.ReclamationService;

import com.backend.Insurance.Reclamation.DTOS.ReclamationDTO;
import com.backend.Insurance.Reclamation.DTOS.ReclamationResponseDTO;
import com.backend.Insurance.Reclamation.Reclamation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/reclamation")
@RequiredArgsConstructor
public class ReclamationController {
    private final ReclamationService reclamationService;
    private final ObjectMapper objectMapper;


    @PostMapping("/CreerReclamation")
    public ResponseEntity<String> uploadReclamation(
            @RequestParam("file") MultipartFile file,
            @RequestParam("claim") String reclamationJson) {
        try {
            ReclamationDTO reclamationDTO = objectMapper.readValue(reclamationJson, ReclamationDTO.class);
            return reclamationService.CreerReclamation(reclamationDTO,file);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/changerstatus/{ReclamationID}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> ChangerStatus(
            @PathVariable Long ReclamationID,
            @RequestBody ReclamationDTO reclamationDTO
    ){
        return reclamationService.ChangerStatus(ReclamationID , reclamationDTO);
    }
    @GetMapping("/getusersrelamations")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReclamationResponseDTO>> RetrieveReclamations (){
        return reclamationService.RetrieveReclamations();
    }
    @GetMapping("/getrelamations/{userEmail}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reclamation>> GetUserRelamations (
            @PathVariable String userEmail
    ){
        return reclamationService.GetUserRelamations(userEmail);
    }
    @GetMapping("/getreclamation/{reclamationId}")
    public ResponseEntity<ReclamationResponseDTO> GetRelamation (
            @PathVariable Long reclamationId
    ){
        return reclamationService.GetRelamation(reclamationId);
    }

}
