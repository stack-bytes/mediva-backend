package com.stackbytes.service;

import com.stackbytes.UtilTypes;
import com.stackbytes.model.MapUtil;
import com.stackbytes.model.NormalizedMapUtil;
import com.stackbytes.model.dto.CreateMapUtilRequestDto;
import com.stackbytes.model.dto.CreateMapUtilResponseDto;
import com.stackbytes.model.dto.GetAllUtilsResponseDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {

    MongoTemplate mongoTemplate;

    MapService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Boolean createMapUtil(CreateMapUtilRequestDto createMapUtilRequestDto) {

        System.out.println(createMapUtilRequestDto.getLocationName());
        MapUtil mapUtil = new MapUtil("", createMapUtilRequestDto.getLocationName(), Pair.of(createMapUtilRequestDto.getLocationX(), createMapUtilRequestDto.getLocationY()), createMapUtilRequestDto.getType());


        NormalizedMapUtil normalizedMapUtil = NormalizedMapUtil.builder()
                .locationName(mapUtil.getLocationName())
                .locationPhoto("")
                .coordinates(mapUtil.getCoordinates())
                .type(mapUtil.getType().getType())
                .build();


        NormalizedMapUtil returnedUtil = mongoTemplate.insert(normalizedMapUtil);


        return true;


    }

    public List<NormalizedMapUtil> getAllUtils() {
        List<NormalizedMapUtil> utils = mongoTemplate.findAll(NormalizedMapUtil.class);


        return utils;
    }
}
