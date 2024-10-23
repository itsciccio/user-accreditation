package com.yieldstreet.takehome.user_accreditation.dto.request;

import com.yieldstreet.takehome.user_accreditation.enums.AccreditationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FinalizeAccreditationRequestDTO {
    @NotNull(message = "Outcome is required")
    private AccreditationStatus outcome;
}
