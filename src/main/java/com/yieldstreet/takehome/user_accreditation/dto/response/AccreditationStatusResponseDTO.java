package com.yieldstreet.takehome.user_accreditation.dto.response;

import com.yieldstreet.takehome.user_accreditation.enums.AccreditationStatus;
import com.yieldstreet.takehome.user_accreditation.enums.AccreditationType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccreditationStatusResponseDTO {
    private AccreditationType accreditationType;
    private AccreditationStatus accreditationStatus;
}
