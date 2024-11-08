package com.stackbytes.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PrescriptionCreateResponseDto {
    MultipartFile prescriptionFile;
}
