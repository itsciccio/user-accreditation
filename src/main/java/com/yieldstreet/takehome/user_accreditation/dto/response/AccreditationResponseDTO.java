package com.yieldstreet.takehome.user_accreditation.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccreditationResponseDTO {
    private UUID accredtationId;
}
