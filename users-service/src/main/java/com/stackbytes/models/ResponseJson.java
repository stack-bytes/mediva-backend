package com.stackbytes.models;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@Builder
@Getter
public class ResponseJson {
    private int code;
    private boolean status;
    private String message;
    private String token;
    private HashMap<String,String> gpg;
}
