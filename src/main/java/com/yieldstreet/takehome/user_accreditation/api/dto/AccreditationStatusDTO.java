package com.yieldstreet.takehome.user_accreditation.api.dto;

import com.yieldstreet.takehome.user_accreditation.enums.AccreditationStatus;
import com.yieldstreet.takehome.user_accreditation.enums.AccreditationType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccreditationStatusDTO {
    private AccreditationType accreditationType;
    private AccreditationStatus accreditationStatus;
}
