package com.stackbytes.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
@Getter
@Setter
public class GroupCreateRequestTdo {
    private String name;
    private String description;
    private String ownerId;
    private List<String> members;
    private Double rating;
    private String location;
    private Double geolocationX;
    private Double geolocationY;
}
