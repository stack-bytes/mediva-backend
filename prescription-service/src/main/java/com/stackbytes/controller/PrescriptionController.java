package com.stackbytes.controller;

import com.stackbytes.model.dto.PrescriptionCreateResponseDto;
import com.stackbytes.service.PrescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<PrescriptionCreateResponseDto> createPrescription(@RequestParam String illnessId, @RequestParam String doctorId, @RequestParam MultipartFile prescriptionFile) {
        return ResponseEntity.ok(prescriptionService.createPrescription(illnessId, doctorId, prescriptionFile));
    }
}
