package com.stackbytes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = "symptoms")
@Builder
@Getter
@Setter
public class Symptom {
    @Id
    private String id;
    private String name;
    private String description;
    private int severity;
    private boolean emergency;
    private Date date;
    private String userId;
    private List<String> doctorId;
}

