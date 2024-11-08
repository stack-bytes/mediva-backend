package com.stackbytes.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseJson {
    private int code;
    private boolean status;
    private String message;
    private String token;
}
