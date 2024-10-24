package com.yieldstreet.takehome.user_accreditation.exceptions;

import java.util.UUID;

public class AccreditationNotFoundException extends RuntimeException{
    public AccreditationNotFoundException(UUID accreditationId) {
        super("Accreditation not found with id: " + accreditationId);
    }
}
