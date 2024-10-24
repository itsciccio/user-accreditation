package com.yieldstreet.takehome.user_accreditation.service;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yieldstreet.takehome.user_accreditation.dto.request.CreateAccreditationRequestDTO;
import com.yieldstreet.takehome.user_accreditation.dto.request.DocumentRequestDTO;
import com.yieldstreet.takehome.user_accreditation.dto.request.FinalizeAccreditationRequestDTO;
import com.yieldstreet.takehome.user_accreditation.dto.response.AccreditationResponseDTO;
import com.yieldstreet.takehome.user_accreditation.dto.response.AccreditationStatusResponseDTO;
import com.yieldstreet.takehome.user_accreditation.dto.response.AccreditationsForUserResponseDTO;
import com.yieldstreet.takehome.user_accreditation.enums.AccreditationStatus;
import com.yieldstreet.takehome.user_accreditation.exceptions.AccreditationNotFoundException;
import com.yieldstreet.takehome.user_accreditation.exceptions.InvalidAccreditationStatusChangeException;
import com.yieldstreet.takehome.user_accreditation.exceptions.FailedAccreditationStatusUpdateException;
import com.yieldstreet.takehome.user_accreditation.exceptions.PendingAccreditationAlreadyExistsForUserException;
import com.yieldstreet.takehome.user_accreditation.model.Accreditation;
import com.yieldstreet.takehome.user_accreditation.model.Document;
import com.yieldstreet.takehome.user_accreditation.repository.AccreditationRepository;
import com.yieldstreet.takehome.user_accreditation.repository.DocumentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccreditationServiceImpl implements AccreditationService{
    private final AccreditationRepository accreditationRepository;
    private final DocumentRepository documentRepository;

    @Override
    public AccreditationResponseDTO createAccreditation(CreateAccreditationRequestDTO request) throws PendingAccreditationAlreadyExistsForUserException {
        if(accreditationRepository.findByUserId(request.getUserId()).stream()
            .anyMatch(accreditation -> accreditation.getAccreditationStatus().equals(AccreditationStatus.PENDING))){
                throw new PendingAccreditationAlreadyExistsForUserException(request.getUserId());    
        }

        Document document = new Document();
        
        DocumentRequestDTO documentDTO = request.getDocument();

        document.setName(documentDTO.getName());
        document.setMimeType(documentDTO.getMimeType());
        document.setContent(documentDTO.getContent());

        documentRepository.save(document);

        Accreditation accreditation = new Accreditation();
        
        accreditation.setUserId(request.getUserId());
        accreditation.setAccreditationType(request.getAccreditationType());
        accreditation.setDocument(document);

        Accreditation savedAccreditation = accreditationRepository.save(accreditation);

        return mapToResponse(savedAccreditation);
    }

    @Override
    public AccreditationResponseDTO finalizeAccreditation(UUID accreditationId, FinalizeAccreditationRequestDTO request) 
        throws AccreditationNotFoundException, FailedAccreditationStatusUpdateException, InvalidAccreditationStatusChangeException {
        Accreditation accreditation = accreditationRepository.findById(accreditationId)
            .orElseThrow(() -> new AccreditationNotFoundException(accreditationId));

        if(accreditation.getAccreditationStatus().equals(AccreditationStatus.FAILED)){
            throw new FailedAccreditationStatusUpdateException();
        }

        // Note: Clarification with business/product team may be needed if CONFIRMED accreditations should:
        // 1. Only be allowed to transition to EXPIRED status
        // 2. Or if other status transitions are permitted
        // For the time being, we stick to option 1.
        if(accreditation.getAccreditationStatus().equals(AccreditationStatus.CONFIRMED) 
            && !request.getOutcome().equals(AccreditationStatus.EXPIRED)){
            throw new InvalidAccreditationStatusChangeException();
        }
        
        accreditation.setAccreditationStatus(request.getOutcome());

        Accreditation savedAccreditation = accreditationRepository.save(accreditation);

        return mapToResponse(savedAccreditation);
    }

    private AccreditationResponseDTO mapToResponse(Accreditation accreditation) {
        return AccreditationResponseDTO.builder()
            .accredtationId(accreditation.getId())
            .build();
    }

    @Override
    public AccreditationsForUserResponseDTO getAccreditationForUser(String userId) {
        Map<UUID, AccreditationStatusResponseDTO> accreditationsForUser = accreditationRepository.findByUserId(userId).stream()
            .collect(Collectors.toMap(
                Accreditation::getId, 
                accreditation -> AccreditationStatusResponseDTO.builder()
                    .accreditationType(accreditation.getAccreditationType())
                    .accreditationStatus(accreditation.getAccreditationStatus())
                    .build()
                )
            );
        
        return mapToResponse(userId, accreditationsForUser);
    }

    private AccreditationsForUserResponseDTO mapToResponse(String userId, Map<UUID, AccreditationStatusResponseDTO> accreditationStatus) {
        return AccreditationsForUserResponseDTO.builder()
            .userId(userId)
            .accreditationStatuses(accreditationStatus)
            .build();
    }
}
