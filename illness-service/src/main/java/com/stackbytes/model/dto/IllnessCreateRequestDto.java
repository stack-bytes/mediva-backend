package com.stackbytes.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class IllnessCreateRequestDto {
    private String name;
    private String description;
    private String pacientId;
    private String doctorId;
    private String prescriptionId;
    private List<String> symptomsId;
    private Date date;
    private List<String> tags;
}
