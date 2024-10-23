package com.yieldstreet.takehome.user_accreditation.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DocumentDTO {
    @NotBlank(message = "Document name is required")
    private String name;
    
    @JsonProperty("mime_type") 
    @NotBlank(message = "MIME type is required")
    private String mimeType;
    
    @NotBlank(message = "Content is required")
    @Pattern(regexp = "^[A-Za-z0-9+/=]*$", message = "Invalid Base64 content")
    private String content;
}
