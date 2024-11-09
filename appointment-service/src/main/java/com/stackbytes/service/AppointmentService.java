package com.stackbytes.service;

import com.stackbytes.model.Appointment;
import com.stackbytes.model.dto.CreateAppointmentRequestDto;
import com.stackbytes.model.dto.CreateAppointmentResponseDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import com.stackbytes.utils.QuerryingUtils;

import java.util.List;

@Service
public class AppointmentService {

    private final MongoTemplate mongoTemplate;
    private final QuerryingUtils<Appointment, String> queryUtils;

    public AppointmentService(MongoTemplate mongoTemplate, QuerryingUtils<Appointment, String> queryUtils) {
        this.mongoTemplate = mongoTemplate;
        this.queryUtils =  queryUtils;
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


    public List<Appointment> getKeyAppointments(String userId, String key) {
        List<Appointment> appointments = queryUtils.getKeyValueList(key, userId, mongoTemplate, Appointment.class);
        return appointments;
    }



}
