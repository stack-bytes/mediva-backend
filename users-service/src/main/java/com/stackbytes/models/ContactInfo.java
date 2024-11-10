package com.stackbytes.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
public class ContactInfo {
    private String phone;
    private String email;
}
