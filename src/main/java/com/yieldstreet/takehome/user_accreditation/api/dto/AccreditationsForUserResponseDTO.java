package com.yieldstreet.takehome.user_accreditation.api.dto;

import java.util.Map;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccreditationsForUserResponseDTO {
    private String userId;
    private Map<UUID, AccreditationStatusDTO> accreditationStatuses;
}
