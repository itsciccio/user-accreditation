package com.yieldstreet.takehome.user_accreditation.dto.response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccreditationsForUserResponseDTO {
    private String userId;
    @Builder.Default
    private Map<UUID, AccreditationStatusResponseDTO> accreditationStatuses = new HashMap<>();
}
