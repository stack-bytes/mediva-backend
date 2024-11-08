package com.stackbytes.service;

import com.mongodb.client.result.UpdateResult;
import com.stackbytes.model.Illness;
import com.stackbytes.model.dto.IllnessCreateRequestDto;
import com.stackbytes.model.dto.IllnessCreateResponseDto;
import com.stackbytes.model.dto.IllnessGetResponseDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                .symptomsId(illnessCreateRequestDto.getSymptomsId())
                .prescriptionId(illnessCreateRequestDto.getPrescriptionId())
                .build();

        Illness createdObject = mongoTemplate.insert(newIllnessObject);

        IllnessCreateResponseDto createdIllnessDto = IllnessCreateResponseDto.builder()
                .id(createdObject.getId())
                .build();

        return createdIllnessDto;

    }

    public Boolean addSymptom(String illnessId, String symptomId) {
        Query query = Query.query(Criteria.where("_id").is(illnessId));
        Update update = new Update().addToSet("symptomsId", symptomId);

        UpdateResult updatedObject = mongoTemplate.updateFirst(query, update, Illness.class);

        return updatedObject.getMatchedCount() > 0;
    }

    public List<IllnessGetResponseDto> getIllness(String userId) {
        List<Illness> foundIllnesses = mongoTemplate.find(
                new Query().addCriteria(Criteria.where("pacientId").is(userId)),
                Illness.class
        );

        List<IllnessGetResponseDto> illnessGetResponseDto = new ArrayList<>();



        for(Illness illness : foundIllnesses) {
            illnessGetResponseDto.add(
                    IllnessGetResponseDto.builder()
                            .id(illness.getId())
                            .pacientId(illness.getPacientId())
                            .doctorId(illness.getDoctorId())
                            .tags(illness.getTags())
                            .name(illness.getName())
                            .description(illness.getDescription())
                            .prescriptionId(illness.getPrescriptionId())
                            .build()
            );
        }

        return illnessGetResponseDto;
    }
}
