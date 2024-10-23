package com.yieldstreet.takehome.user_accreditation.api;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yieldstreet.takehome.user_accreditation.api.dto.CreateAccreditationRequestDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.AccreditationResponseDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.AccreditationsForUserResponseDTO;
import com.yieldstreet.takehome.user_accreditation.api.dto.FinalizeAccreditationRequestDTO;
import com.yieldstreet.takehome.user_accreditation.service.AccreditationService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class AccreditationController {
    private final AccreditationService accreditationService;

    @PostMapping("/accreditation")
    public ResponseEntity<AccreditationResponseDTO> createAccreditation(
        @Validated @RequestBody CreateAccreditationRequestDTO request) {
        AccreditationResponseDTO response = accreditationService.createAccreditation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/accreditation/{accreditationId}")
    public ResponseEntity<AccreditationResponseDTO> finalizeAccreditation(
        @PathVariable UUID accreditationId, 
        @Validated @RequestBody FinalizeAccreditationRequestDTO request) throws Exception {
        AccreditationResponseDTO response = accreditationService.finalizeAccreditation(accreditationId, request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{userId}/accreditation")
    public ResponseEntity<AccreditationsForUserResponseDTO> getAccreditationsForUser(
        @PathVariable String userId) throws Exception {
        AccreditationsForUserResponseDTO response = accreditationService.getAccreditationForUser(userId);
        return ResponseEntity.ok(response);
    }
    
}