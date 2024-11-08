package com.stackbytes.model.dto;

import lombok.Data;
import org.springframework.data.util.Pair;

@Data
public class CreateAppointmentRequestDto {
    private String title;
    private String doctorId;
    private String pacientId;
    private String location;
    private Pair<Double, Double> geolocation;
}
