package com.stackbytes.service;

import com.stackbytes.model.Illness;
import com.stackbytes.model.dto.IllnessCreateRequestDto;
import com.stackbytes.model.dto.IllnessCreateResponseDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class IllnessService {


    /* Templates */

    private final MongoTemplate mongoTemplate;

    IllnessService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public IllnessCreateResponseDto createIllness(IllnessCreateRequestDto illnessCreateRequestDto) {
        Illness newIllnessObject = Illness.builder()
                .name(illnessCreateRequestDto.getName())
                .description(illnessCreateRequestDto.getDescription())
                .doctorId(illnessCreateRequestDto.getDoctorId())
                .pacientId(illnessCreateRequestDto.getPacientId())
                .tags(illnessCreateRequestDto.getTags())
                .prescriptionId(illnessCreateRequestDto.getPrescriptionId())
                .build();

        Illness createdObject = mongoTemplate.insert(newIllnessObject);

        IllnessCreateResponseDto illnessCreateResponse = IllnessCreateResponseDto.builder()
                .status("Illness added succesfully")
                .build();

        return illnessCreateResponse;
    }
}
