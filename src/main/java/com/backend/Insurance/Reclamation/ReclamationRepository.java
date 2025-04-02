package com.backend.Insurance.Reclamation;

import com.backend.Insurance.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation , Long> {
    List<Reclamation> findByUserId(Long id);
}
