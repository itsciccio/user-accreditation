package com.yieldstreet.takehome.user_accreditation.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yieldstreet.takehome.user_accreditation.enums.AccreditationStatus;
import com.yieldstreet.takehome.user_accreditation.model.Accreditation;
import com.yieldstreet.takehome.user_accreditation.repository.AccreditationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccreditationScheduler {
    
    private final AccreditationRepository accreditationRepository;

    // Run at midnight every day
    // TODO: potential improvement: make the cron configurable
    // For testing each minute: cron = "0 * * * * *"
    @Scheduled(cron = "0 0 0 * * *")
    public void expireOldAccreditations() {
        log.info("Starting scheduled job to expire old accreditations");
        
        // For testing each minute:
        // LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        
        List<Accreditation> expiredAccreditations = accreditationRepository
            .findByAccreditationStatusAndUpdatedAtBefore(
                AccreditationStatus.CONFIRMED, 
                thirtyDaysAgo
            );

        if (!expiredAccreditations.isEmpty()) {
            expiredAccreditations.forEach(accreditation -> {
                accreditation.setAccreditationStatus(AccreditationStatus.EXPIRED);
                log.info("Expiring accreditation ID: {}", accreditation.getId());
            });
            
            accreditationRepository.saveAll(expiredAccreditations);
            log.info("Expired {} accreditations", expiredAccreditations.size());
        } else {
            log.info("No old accreditations to expire");
        }
    }
}
