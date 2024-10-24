package com.yieldstreet.takehome.user_accreditation.service;

import java.util.UUID;

import com.yieldstreet.takehome.user_accreditation.dto.request.CreateAccreditationRequestDTO;
import com.yieldstreet.takehome.user_accreditation.dto.request.FinalizeAccreditationRequestDTO;
import com.yieldstreet.takehome.user_accreditation.dto.response.AccreditationResponseDTO;
import com.yieldstreet.takehome.user_accreditation.dto.response.AccreditationsForUserResponseDTO;
import com.yieldstreet.takehome.user_accreditation.exceptions.AccreditationNotFoundException;
import com.yieldstreet.takehome.user_accreditation.exceptions.FailedAccreditationStatusUpdateException;
import com.yieldstreet.takehome.user_accreditation.exceptions.InvalidAccreditationStatusChangeException;
import com.yieldstreet.takehome.user_accreditation.exceptions.PendingAccreditationAlreadyExistsForUserException;

public interface AccreditationService {
    AccreditationResponseDTO createAccreditation(CreateAccreditationRequestDTO request) 
        throws PendingAccreditationAlreadyExistsForUserException; 
    AccreditationResponseDTO finalizeAccreditation(UUID accreditationId, FinalizeAccreditationRequestDTO request) 
        throws AccreditationNotFoundException, FailedAccreditationStatusUpdateException, InvalidAccreditationStatusChangeException;
    AccreditationsForUserResponseDTO getAccreditationForUser(String userId);
}
