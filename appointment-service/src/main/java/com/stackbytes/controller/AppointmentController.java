package com.stackbytes.controller;


import com.stackbytes.model.dto.CreateAppointmentRequestDto;
import com.stackbytes.model.dto.CreateAppointmentResponseDto;
import com.stackbytes.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppointmentController {

    private final AppointmentService appointmentService;

    AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<CreateAppointmentResponseDto> createAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.ok(appointmentService.createAppointment(createAppointmentRequestDto));
    }

}
