package com.stackbytes.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

import java.util.Date;

@Builder
@Getter
@Document(collection = "appointments")
public class Appointment {
    @Id
    private String id;

    private String title;
    private String pacientId;
    private String doctorId;
    private Date dateTime;
    private String location;
    private Pair<Double, Double> geolocation;
}
