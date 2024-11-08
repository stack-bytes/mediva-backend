package com.stackbytes.controller;

import com.stackbytes.model.IllnessCreateRequestDto;
import com.stackbytes.model.IllnessCreateResponseDto;
import com.stackbytes.model.IllnessGetResponseDto;
import com.stackbytes.service.IllnessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class IllnessController {

    private final IllnessService illnessService;

    public IllnessController(IllnessService illnessService) {
        this.illnessService = illnessService;
    }

    @CrossOrigin
    @PostMapping()
    public ResponseEntity<IllnessCreateResponseDto> createIllness(@RequestBody IllnessCreateRequestDto illnessCreateRequestDto) {
        return ResponseEntity.ok(illnessService.createIllness(illnessCreateRequestDto));
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<List<IllnessGetResponseDto>> getIllnesses(@RequestParam String userId){
        return ResponseEntity.ok(illnessService.getIllness(userId));
    }

    @CrossOrigin
    @PatchMapping()
    public ResponseEntity<String> addSymptom(@RequestParam String illnessId, @RequestParam String symptomId) {
        return illnessService.addSymptom(illnessId, symptomId) ?
                ResponseEntity.ok("Symptom added succesfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not add symptom");
    }



}
