package com.stackbytes.model;

import lombok.Builder;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;


@Setter
@Document
public abstract class MapLocation {
    @Id
    private String id;
    private Pair<Double, Double> coordinates;
}
