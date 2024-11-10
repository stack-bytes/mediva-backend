package com.stackbytes.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
public class LoginData {
    private String email;
    private String password;
    private boolean isMedic;
}
