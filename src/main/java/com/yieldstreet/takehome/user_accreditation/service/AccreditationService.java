package com.yieldstreet.takehome.user_accreditation.service;

import java.util.UUID;

import com.yieldstreet.takehome.user_accreditation.api.dto.CreateAccreditationRequestDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.AccreditationResponseDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.AccreditationsForUserResponseDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.FinalizeAccreditationRequestDTO;

public interface AccreditationService {
    AccreditationResponseDTO createAccreditation(CreateAccreditationRequestDTO request);    
    AccreditationResponseDTO finalizeAccreditation(UUID accreditationId, FinalizeAccreditationRequestDTO request) throws Exception;
    AccreditationsForUserResponseDTO getAccreditationForUser(String userId) throws Exception;
}
