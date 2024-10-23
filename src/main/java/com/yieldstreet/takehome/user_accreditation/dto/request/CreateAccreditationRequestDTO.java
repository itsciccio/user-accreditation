package com.yieldstreet.takehome.user_accreditation.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.yieldstreet.takehome.user_accreditation.enums.AccreditationType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAccreditationRequestDTO {
    @JsonProperty("user_id") 
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @JsonProperty("accreditation_type") 
    @NotNull(message = "Accreditation type is required")
    private AccreditationType accreditationType;
    
    @Valid
    @NotNull(message = "Document is required")
    private DocumentRequestDTO document;
}
