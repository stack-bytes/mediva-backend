package com.stackbytes.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SymptomCreateResponseDto {
    private String id;
    private String message;
}
