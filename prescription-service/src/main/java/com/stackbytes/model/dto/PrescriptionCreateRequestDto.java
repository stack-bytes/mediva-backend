package com.stackbytes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class PrescriptionCreateRequestDto {
    private String id;
    private String illnessId;
    private String medicId;
    private MultipartFile attachment;
}
