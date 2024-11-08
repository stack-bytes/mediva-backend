package com.stackbytes.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SymptomCreateRequestDto {
    private String name;
    private String description;
    private String userId;
    private String severity;
}
