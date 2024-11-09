package com.stackbytes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
public class GroupCreateResponseTdo {
    private String message;
    private String id;
}
