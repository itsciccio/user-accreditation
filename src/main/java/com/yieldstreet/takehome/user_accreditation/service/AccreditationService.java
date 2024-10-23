package com.yieldstreet.takehome.user_accreditation.service;

import java.util.UUID;

import com.yieldstreet.takehome.user_accreditation.dto.request.CreateAccreditationRequestDTO;
import com.yieldstreet.takehome.user_accreditation.dto.request.FinalizeAccreditationRequestDTO;
import com.yieldstreet.takehome.user_accreditation.dto.response.AccreditationResponseDTO;
import com.yieldstreet.takehome.user_accreditation.dto.response.AccreditationsForUserResponseDTO;

public interface AccreditationService {
    AccreditationResponseDTO createAccreditation(CreateAccreditationRequestDTO request);    
    AccreditationResponseDTO finalizeAccreditation(UUID accreditationId, FinalizeAccreditationRequestDTO request) throws Exception;
    AccreditationsForUserResponseDTO getAccreditationForUser(String userId) throws Exception;
}
