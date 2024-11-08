package com.stackbytes.controller;

import com.stackbytes.model.dto.IllnessCreateRequestDto;
import com.stackbytes.model.dto.IllnessCreateResponseDto;
import com.stackbytes.model.dto.IllnessGetResponseDto;
import com.stackbytes.service.IllnessService;
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

    @PostMapping()
    public ResponseEntity<IllnessCreateResponseDto> createIllness(@RequestBody IllnessCreateRequestDto illnessCreateRequestDto) {
        return ResponseEntity.ok(illnessService.createIllness(illnessCreateRequestDto));
    }

    @GetMapping()
    public ResponseEntity<List<IllnessGetResponseDto>> getIllness(@RequestParam String userId){
        return ResponseEntity.ok(illnessService.getIllness(userId));
    }

}
