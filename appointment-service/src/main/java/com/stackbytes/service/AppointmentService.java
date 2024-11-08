package com.stackbytes.service;

import com.stackbytes.model.Appointment;
import com.stackbytes.model.dto.CreateAppointmentRequestDto;
import com.stackbytes.model.dto.CreateAppointmentResponseDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final MongoTemplate mongoTemplate;

    public AppointmentService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public CreateAppointmentResponseDto createAppointment(CreateAppointmentRequestDto createAppointmentRequestDto) {

        Appointment appointment = Appointment.builder()
                .title(createAppointmentRequestDto.getTitle())
                .dateTime(createAppointmentRequestDto.getTime())
                .geolocation(Pair.of(createAppointmentRequestDto.getGeolocationX(), createAppointmentRequestDto.getGeolocationY()))
                .doctorId(createAppointmentRequestDto.getDoctorId())
                .pacientId(createAppointmentRequestDto.getPacientId())
                .location(createAppointmentRequestDto.getLocation())
                .build();


        Appointment apppointmentObject = mongoTemplate.insert(appointment);

        CreateAppointmentResponseDto createAppointmentResponseDto = new CreateAppointmentResponseDto(apppointmentObject.getId());

        return createAppointmentResponseDto;
    }


}
