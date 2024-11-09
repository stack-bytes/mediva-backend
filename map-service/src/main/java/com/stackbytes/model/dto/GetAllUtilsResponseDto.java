package com.stackbytes.model.dto;

import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.util.Pair;

@Data
@Setter
public class GetAllUtilsResponseDto {
    private String id;

    private String locationPhoto;
    private String locationName;
    private Pair<Double, Double> coordinates;
    private String type;
}
