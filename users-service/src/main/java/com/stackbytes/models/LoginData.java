package com.stackbytes.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginData {
    private String email;
    private String password;
}
