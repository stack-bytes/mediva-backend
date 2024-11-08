package com.stackbytes.controller;

import com.stackbytes.model.dto.PrescriptionCreateRequestDto;
import com.stackbytes.model.dto.PrescriptionCreateResponseDto;
import com.stackbytes.service.PrescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<PrescriptionCreateResponseDto> createPrescription(@RequestBody PrescriptionCreateRequestDto prescriptionCreateRequestDto) {
        return ResponseEntity.ok(prescriptionService.createPrescription(prescriptionCreateRequestDto));
    }
}
