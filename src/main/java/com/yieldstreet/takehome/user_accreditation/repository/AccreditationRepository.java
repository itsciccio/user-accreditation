package com.yieldstreet.takehome.user_accreditation.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yieldstreet.takehome.user_accreditation.enums.AccreditationStatus;
import com.yieldstreet.takehome.user_accreditation.model.Accreditation;

@Repository
public interface AccreditationRepository extends JpaRepository<Accreditation, UUID> {
    List<Accreditation> findByUserId(String userId);
    List<Accreditation> findByAccreditationStatusAndUpdatedAtBefore(
        AccreditationStatus status, 
        LocalDateTime date
    );
}