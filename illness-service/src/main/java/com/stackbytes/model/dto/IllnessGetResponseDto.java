package com.stackbytes.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IllnessGetResponseDto{
    private String id;

    private String name;
    private String description;
    private String pacientId;
    private String doctorId;
    private String prescriptionId;
    private List<String> symptomsId;
    private List<String> tags;
}
