package com.stackbytes.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

@Document(collection = "users")
@Builder
@Getter
@Setter
@Data
public class User {
    @Id
    private String id;
    private List<String> illnesses;
    private String fullName;
    private  String username;
    private String email;
    private String password;
    private boolean medic;
    private HashMap<String,String> gpg;
    private List<String> doctors;
    private List<String> symptoms;
    private List<String> appointments;
    private List<String> groups;
}
