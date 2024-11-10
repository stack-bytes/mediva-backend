package com.stackbytes.model.dto;

import com.stackbytes.model.Illness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
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
    private Date date;
}
