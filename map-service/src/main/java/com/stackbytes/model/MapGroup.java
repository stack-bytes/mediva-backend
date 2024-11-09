package com.stackbytes.model;

import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public class MapGroup extends MapLocation{
    private String groupName;
    private String description;
    private List<Date> meetingDates;
    private Integer participants;
    private Integer rating;
}
