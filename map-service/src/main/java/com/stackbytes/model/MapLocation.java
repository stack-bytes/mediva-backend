package com.stackbytes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;


@Getter
@Setter
@Document(collection = "locations")
public abstract class MapLocation {
    @Id
    private String id;

    private String locationPhoto;
    private String locationName;
    private Pair<Double, Double> coordinates;

    public MapLocation(String locationPhoto, String locationName, Pair<Double, Double> coordinates) {
        this.locationPhoto = locationPhoto;
        this.locationName = locationName;
        this.coordinates = coordinates;
    }
}
