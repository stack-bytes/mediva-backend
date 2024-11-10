package com.stackbytes.service;

import com.stackbytes.model.Symptom;
import com.stackbytes.model.dto.SymptomCreateRequestDto;
import com.stackbytes.model.dto.SymptomCreateResponseDto;
import com.stackbytes.utils.QuerryingUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class SymptomService {

    private final MongoTemplate mongoTemplate;
    private final QuerryingUtils<Symptom, String> querryingUtils;


    public SymptomService(MongoTemplate mongoTemplate, QuerryingUtils<Symptom, String> querryingUtils) {
        this.mongoTemplate = mongoTemplate;
        this.querryingUtils = querryingUtils; //singleton by default
    }


    public Pair<List<SymptomCreateResponseDto>, Boolean> createSymptom(List<SymptomCreateRequestDto> symptomCreateRequestDto){

        List<SymptomCreateResponseDto> responseDto = new ArrayList<>();

        for(SymptomCreateRequestDto s: symptomCreateRequestDto){

            Symptom symptom = Symptom.builder()
                    .name(s.getName())
                    .description(s.getDescription())
                    .userId(s.getUserId())
                    .severity(s.getSeverity())
                    .emergency(s.getEmergency())
                    .date(s.getDate())
                    .doctorId(s.getDoctorId())
                    .build();

            System.out.println("DoctorID: " + s.getDoctorId() + "\n" + "UserId:" + s.getUserId());

            Symptom createdSymptom = mongoTemplate.insert(symptom);


             responseDto.add(SymptomCreateResponseDto.builder()
                            .id(createdSymptom.getId())
                            .message("Symptom succesfully reported to doctor!")
                            .build()
             );

        }



        return Pair.of(responseDto, true);
    }

    public List<Symptom> getSymptoms(String userId){
        List<Symptom> results = mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), Symptom.class);
        System.out.println(results.getLast().getDate());
        return results;
    }

    public Boolean verifySymptom (String symptomId){
        Symptom foundSymptom = mongoTemplate.findById(symptomId, Symptom.class);

        return foundSymptom != null;
    }
}
