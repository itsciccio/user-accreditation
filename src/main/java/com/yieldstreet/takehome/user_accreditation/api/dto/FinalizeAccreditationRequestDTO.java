package com.yieldstreet.takehome.user_accreditation.api.dto;

import com.yieldstreet.takehome.user_accreditation.enums.AccreditationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FinalizeAccreditationRequestDTO {
    @NotNull(message = "Outcome is required")
    private AccreditationStatus outcome;
}
