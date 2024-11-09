package com.stackbytes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;


public class MapGroup extends MapLocation{
    private String description;
    private List<Date> meetingDates;
    private Integer participants;
    private Integer rating;


    public MapGroup(String locationPhoto, String locationName, Pair<Double, Double> coordinates, String description, List<Date> meetingDates, Integer participants, Integer rating) {
        super(locationPhoto, locationName, coordinates);
        this.description = description;
        this.meetingDates = meetingDates;
        this.participants = participants;
        this.rating = rating;
    }
}
