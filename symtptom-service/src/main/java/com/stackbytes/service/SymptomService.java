package com.stackbytes.service;

import com.stackbytes.model.Symptom;
import com.stackbytes.model.dto.SymptomCreateRequestDto;
import com.stackbytes.model.dto.SymptomCreateResponseDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SymptomService {

    private final MongoTemplate mongoTemplate;

    public SymptomService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public Pair<List<SymptomCreateResponseDto>, Boolean> createSymptom(List<SymptomCreateRequestDto> symptomCreateRequestDto){

        List<SymptomCreateResponseDto> responseDto = new ArrayList<>();

        for(SymptomCreateRequestDto s: symptomCreateRequestDto){

            Symptom symptom = Symptom.builder()
                    .name(s.getName())
                    .description(s.getDescription())
                    .userId(s.getUserId())
                    .severity(s.getSeverity())
                    .build();

            Symptom createdSymptom = mongoTemplate.insert(symptom);


             responseDto.add(SymptomCreateResponseDto.builder()
                            .id(createdSymptom.getId())
                            .message("Symptom succesfully reported to doctor!")
                            .build()
             );

        }



        return Pair.of(responseDto, true);
    }

    public Boolean verifySymptom (String symptomId){
        Symptom foundSymptom = mongoTemplate.findById(symptomId, Symptom.class);

        return foundSymptom != null;
    }
}
