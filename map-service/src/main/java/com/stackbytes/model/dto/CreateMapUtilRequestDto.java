package com.stackbytes.model.dto;

import lombok.Data;

@Data
public class CreateMapUtilRequestDto {
    private Double locationX;
    private Double locationY;
    private String type;
    private String locationName;
}
