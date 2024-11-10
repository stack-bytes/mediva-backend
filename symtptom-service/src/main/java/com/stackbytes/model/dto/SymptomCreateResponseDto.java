package com.stackbytes.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class SymptomCreateResponseDto {
    private String id;
    private String message;
}
