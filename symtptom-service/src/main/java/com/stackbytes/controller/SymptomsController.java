package com.stackbytes.controller;

import com.stackbytes.model.Symptom;
import com.stackbytes.model.dto.SymptomCreateRequestDto;
import com.stackbytes.model.dto.SymptomCreateResponseDto;
import com.stackbytes.service.SymptomService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SymptomsController {

    private final SymptomService symptomService;

    public SymptomsController(SymptomService symptomService) {
        this.symptomService = symptomService;
    }


    @CrossOrigin
    @GetMapping("verify")
    public ResponseEntity<Boolean> verifySymptom(@RequestParam String symptomId){
        return ResponseEntity.ok(symptomService.verifySymptom(symptomId));
    }

    @CrossOrigin
    @GetMapping("user")
    public ResponseEntity<List<Symptom>>  getUserSymptoms(@RequestParam String userId){
        return ResponseEntity.ok(symptomService.getSymptoms(userId));
    }


    @CrossOrigin
    @PostMapping()
    public ResponseEntity<List<SymptomCreateResponseDto>> createSymptom(@RequestBody List<SymptomCreateRequestDto> requestDto) {
        Pair<List<SymptomCreateResponseDto>, Boolean> symptomResponseDtoStatus = symptomService.createSymptom(requestDto);

        return symptomResponseDtoStatus.getSecond() ?
                ResponseEntity.ok(symptomResponseDtoStatus.getFirst()) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // WS Endpoint Live Notifs

}
