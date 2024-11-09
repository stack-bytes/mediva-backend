package com.stackbytes.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

@Data
@Document(collection = "locations")
@Builder
public class NormalizedMapUtil {
    @Id
    private String id;

    private String locationPhoto;
    private String locationName;
    private Pair<Double, Double> coordinates;


    String type;
}
