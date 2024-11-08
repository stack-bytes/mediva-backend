package com.stackbytes.model;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prescriptions")
@Builder
public class Prescription {
    @Id
    private String id;

    private String illnessId;

    private String medicId;

    private String attachmentId;

    private String signature;

    private String publicKey;

}