package com.stackbytes.model.dto;

import com.stackbytes.model.Illness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

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
