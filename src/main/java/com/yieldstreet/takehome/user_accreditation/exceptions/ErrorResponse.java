package com.yieldstreet.takehome.user_accreditation.exceptions;

import lombok.Value;

@Value
public class ErrorResponse {
    private final int status;
    private final String message;
}