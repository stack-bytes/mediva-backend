package com.stackbytes.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Getter
@Setter
public class SymptomCreateRequestDto {
    private String name;
    private String description;
    private String userId;
    private List<String> doctorId;
    private Boolean  emergency;
    private Date date;
    private int severity;
}
