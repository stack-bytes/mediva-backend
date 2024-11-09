package com.stackbytes.controller;

import brave.Response;
import com.stackbytes.model.MapUtil;
import com.stackbytes.model.NormalizedMapUtil;
import com.stackbytes.model.dto.CreateMapUtilRequestDto;
import com.stackbytes.model.dto.CreateMapUtilResponseDto;
import com.stackbytes.model.dto.GetAllUtilsResponseDto;
import com.stackbytes.service.MapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MapController {

    private final MapService mapService;

    MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @CrossOrigin
    @PostMapping("util")
    public ResponseEntity<Boolean> createMapUtil(@RequestBody CreateMapUtilRequestDto createMapUtilRequestDto) {
        return mapService.createMapUtil(createMapUtilRequestDto) ? ResponseEntity.ok(true) : ResponseEntity.ok(false);
    }

    @CrossOrigin
    @GetMapping("util")
    public ResponseEntity<List<NormalizedMapUtil>> getMapUtil() {
        return ResponseEntity.ok(mapService.getAllUtils());
    }
}
