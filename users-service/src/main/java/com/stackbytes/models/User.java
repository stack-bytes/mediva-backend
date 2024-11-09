package com.stackbytes.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.Date;
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
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String avatar;
    private Medic medic;
    private List<String> doctorsId;
    private Date createdAt;
    private Date updatedAt;
}
