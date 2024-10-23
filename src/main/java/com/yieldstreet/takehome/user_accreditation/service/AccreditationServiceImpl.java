package com.yieldstreet.takehome.user_accreditation.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yieldstreet.takehome.user_accreditation.api.dto.CreateAccreditationRequestDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.AccreditationResponseDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.AccreditationStatusDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.AccreditationsForUserResponseDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.DocumentDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.FinalizeAccreditationRequestDTO;
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
    public AccreditationResponseDTO createAccreditation(CreateAccreditationRequestDTO request) {
        Document document = new Document();
        
        DocumentDTO documentDTO = request.getDocument();

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
    public AccreditationResponseDTO finalizeAccreditation(UUID accreditationId, FinalizeAccreditationRequestDTO request) throws Exception {
        Accreditation accreditation = accreditationRepository.findById(accreditationId)
            .orElseThrow(() -> new Exception("Accreditation not found with id: " + accreditationId));

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
    public AccreditationsForUserResponseDTO getAccreditationForUser(String userId) throws Exception {
        Map<UUID, AccreditationStatusDTO> accreditationsForUser = accreditationRepository.findByUserId(userId).stream()
            .collect(Collectors.toMap(
                Accreditation::getId, 
                accreditation -> AccreditationStatusDTO.builder()
                    .accreditationType(accreditation.getAccreditationType())
                    .accreditationStatus(accreditation.getAccreditationStatus())
                    .build()
                )
            );
        
        return mapToResponse(userId, accreditationsForUser);
    }

    private AccreditationsForUserResponseDTO mapToResponse(String userId, Map<UUID, AccreditationStatusDTO> accreditationStatus) {
        return AccreditationsForUserResponseDTO.builder()
            .userId(userId)
            .accreditationStatuses(accreditationStatus)
            .build();
    }
}
