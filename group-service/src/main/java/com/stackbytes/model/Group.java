package com.stackbytes.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

import java.util.List;

@Document(collection = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Group {
    @Id
    private String id;

    private String name;
    private String description;
    private String ownerId;
    private List<String> membersId;
    private Double rating;
    private String location;
    private Pair<Double, Double> geolocation;


    public Group(String name, String description, String ownerId, List<String> membersId, Double rating, String location, Pair<Double, Double> geolocation) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.membersId = membersId;
        this.rating = rating;
        this.location = location;
        this.geolocation = geolocation;
    }
}
