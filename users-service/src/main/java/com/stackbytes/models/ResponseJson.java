package com.stackbytes.models;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.util.Pair;

import java.util.HashMap;

@Builder
@Getter
public class ResponseJson {
    private int code;
    private boolean status;
    private String message;
    private String token;
    private Pair<String,String> gpg;
}
