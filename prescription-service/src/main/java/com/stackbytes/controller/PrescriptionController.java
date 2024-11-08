package com.stackbytes.controller;


import com.stackbytes.model.dto.PrescriptionCreateRequestDto;
import com.stackbytes.model.dto.PrescriptionCreateResponseDto;
import com.stackbytes.model.dto.PrescriptionGetResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class PrescriptionController {

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<PrescriptionCreateResponseDto> createPrescription(@RequestBody PrescriptionCreateRequestDto prescriptionCreateRequestDto) {
        return null;
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<PrescriptionGetResponseDto> getPrescription(){
        return null;
    }

}
