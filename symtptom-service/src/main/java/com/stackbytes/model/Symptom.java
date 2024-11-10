package com.stackbytes.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = "symptoms")
@Builder
@Getter
@Setter
@Data
public class Symptom {
    @Id
    private String id;
    private String name;
    private String description;
    private int severity;
    private Boolean emergency;
    private Date date;
    private String userId;
    private List<String> doctorId;
}

