package com.yieldstreet.takehome.user_accreditation.exceptions;

public class PendingAccreditationAlreadyExistsForUserException extends RuntimeException{
    public PendingAccreditationAlreadyExistsForUserException(String userId) {
        super("User " + userId + " already has a PENDING accreditation.");
    }
}
