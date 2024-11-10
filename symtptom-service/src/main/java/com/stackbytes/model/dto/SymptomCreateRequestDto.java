package com.stackbytes.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class SymptomCreateRequestDto {
    private String name;
    private String description;
    private String userId;
    private List<String> doctorId;
    private Boolean emergency;
    private Date date;
    private Integer severity;
}
