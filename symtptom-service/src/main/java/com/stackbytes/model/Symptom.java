package com.stackbytes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "symptoms")
@Builder
@Getter
public class Symptom {
    @Id
    private String id;
    private String name;
    private String description;
    private String userId;
    private String severity;
}
