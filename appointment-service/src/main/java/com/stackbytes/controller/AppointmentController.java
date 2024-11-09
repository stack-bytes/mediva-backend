package com.stackbytes.controller;


import com.stackbytes.model.Appointment;
import com.stackbytes.model.dto.CreateAppointmentRequestDto;
import com.stackbytes.model.dto.CreateAppointmentResponseDto;
import com.stackbytes.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("pacient")
    public ResponseEntity<List<Appointment>> getUserAppointments(@RequestParam String userId) {
        return ResponseEntity.ok(appointmentService.getKeyAppointments(userId, "pacientId"));
    }

    @GetMapping("doctor")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(@RequestParam String doctorId) {
        return ResponseEntity.ok(appointmentService.getKeyAppointments(doctorId, "doctorId"));
    }

}
