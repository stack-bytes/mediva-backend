package com.stackbytes.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class RegisterRequestDto {
    private User user;
    private Medic medic;
}
