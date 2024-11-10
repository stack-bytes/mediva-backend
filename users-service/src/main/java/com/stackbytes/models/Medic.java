package com.stackbytes.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Document(collection = "medics")
@Data
@Builder
@Getter
public class Medic {
    @Id
    private String id;
    private String medicalId;
    private String password;
    private Date activeSince;
    private String speciality;
    private String grade;
    private Pair<String,String> gpg;
    private String workPlace;
    private double ratings;
    private String bio;
    private ContactInfo contactInfo;
    private List<String> userId;
    private Date createdAt;
    private Date updatedAt;
}
