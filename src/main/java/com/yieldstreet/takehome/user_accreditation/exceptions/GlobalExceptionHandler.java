package com.yieldstreet.takehome.user_accreditation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PendingAccreditationAlreadyExistsForUserException.class)
    public ResponseEntity<ErrorResponse> handlePendingAccreditationAlreadyExists(
            PendingAccreditationAlreadyExistsForUserException ex
    ) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(AccreditationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccreditationDoesNotExist(
        AccreditationNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FailedAccreditationStatusUpdateException.class)
    public ResponseEntity<ErrorResponse> handleFailedAccreditationStatusAttemptedChange(
        FailedAccreditationStatusUpdateException ex
    ) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAccreditationStatusChangeException.class)
    public ResponseEntity<ErrorResponse> handleConfirmedAccreditationStatusCanOnlyBeExpired(
        InvalidAccreditationStatusChangeException ex
    ) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}