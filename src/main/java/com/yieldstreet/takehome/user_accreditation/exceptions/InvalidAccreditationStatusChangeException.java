package com.yieldstreet.takehome.user_accreditation.exceptions;

public class InvalidAccreditationStatusChangeException extends RuntimeException{
    public InvalidAccreditationStatusChangeException() {
        super("CONFIRMED accreditation can only be EXPIRED.");
    }
}
