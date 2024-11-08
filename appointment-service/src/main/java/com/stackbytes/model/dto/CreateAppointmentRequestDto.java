package com.stackbytes.model.dto;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.Date;

@Data
public class CreateAppointmentRequestDto {
    private String title;
    private String doctorId;
    private String pacientId;
    private String location;
    private Date time;
    private Double geolocationX;
    private Double geolocationY;
}
