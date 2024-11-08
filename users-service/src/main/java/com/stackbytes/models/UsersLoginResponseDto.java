package com.stackbytes.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Hashtable;

@Builder
@Getter
@Data
@ToString
public class UsersLoginResponseDto {
    private String id;
    private String email;
    private String username;
    private boolean isDoctor;
    private String fullName;
}