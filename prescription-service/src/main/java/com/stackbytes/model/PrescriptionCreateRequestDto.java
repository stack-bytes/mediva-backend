package com.stackbytes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class PrescriptionCreateRequestDto {
    private String illnessId;
    private String medicId;
    private MultipartFile attachment;
}