package com.stackbytes.service;

import com.stackbytes.model.Symptom;
import com.stackbytes.model.dto.SymptomCreateRequestDto;
import com.stackbytes.model.dto.SymptomCreateResponseDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class SymptomService {

    private final MongoTemplate mongoTemplate;

    public SymptomService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public Pair<SymptomCreateResponseDto, Boolean> createSymptom(SymptomCreateRequestDto symptomCreateRequestDto){

        Symptom symptom = Symptom.builder()
                .name(symptomCreateRequestDto.getName())
                .description(symptomCreateRequestDto.getDescription())
                .userId(symptomCreateRequestDto.getUserId())
                .severity(symptomCreateRequestDto.getSeverity())
                .build();

        Symptom createdSymptom = mongoTemplate.insert(symptom);

        SymptomCreateResponseDto responseDto = SymptomCreateResponseDto.builder()
                .id(createdSymptom.getId())
                .message("Symptom succesfully reported to doctor!")
                .build();



        return Pair.of(responseDto, true);
    }

    public Boolean verifySymptom (String symptomId){
        Symptom foundSymptom = mongoTemplate.findById(symptomId, Symptom.class);

        return foundSymptom != null;
    }
}
