package com.stackbytes.model;

import com.stackbytes.UtilTypes;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.util.Pair;

@Getter
public class MapUtil extends MapLocation{
    UtilTypes type;

    public MapUtil(String locationPhoto, String locationName, Pair<Double, Double> coordinates, String type) {
        super(locationPhoto, locationName, coordinates);
        switch (type) {
            case "defibrillator":
                this.type = UtilTypes.DEFIBRILLATOR;
                break;
            case "oxygen":
                this.type = UtilTypes.OXYGEN;
                break;
            case "medkit":
                this.type = UtilTypes.MEDKIT;
                break;
            default:
                this.type = null;
                break;

        }
    }
}
