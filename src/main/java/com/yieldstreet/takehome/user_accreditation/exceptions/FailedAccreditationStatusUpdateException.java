package com.yieldstreet.takehome.user_accreditation.exceptions;

public class FailedAccreditationStatusUpdateException extends RuntimeException{
    public FailedAccreditationStatusUpdateException() {
        super("Accreditation is already FAILED; can not be updated further");
    }
}
