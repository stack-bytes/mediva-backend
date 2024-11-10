package com.stackbytes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Document(collection = "illnesses")
public class Illness {
    @Id
    private String id;

    private String name;
    private String description;
    private String pacientId;
    private String doctorId;
    private String prescriptionId;
    private List<String> symptomsId;
    private List<String> tags;
    private Date date;
}
