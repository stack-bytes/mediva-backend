package com.stackbytes.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.result.UpdateResult;
import com.stackbytes.model.Illness;
import com.stackbytes.model.dto.IllnessCreateRequestDto;
import com.stackbytes.model.dto.IllnessCreateResponseDto;
import com.stackbytes.model.dto.IllnessGetResponseDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class IllnessService {

    private final String SymptomServiceAddress = "http://symptom-service:8084/";


    /* Templates */

    private final MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate;
    private final MongoClient mongo;

    IllnessService(MongoTemplate mongoTemplate, RestTemplate restTemplate, MongoClient mongo) {
        this.mongoTemplate = mongoTemplate;
        this.restTemplate = restTemplate;
        this.mongo = mongo;
    }

    public IllnessCreateResponseDto createIllness(IllnessCreateRequestDto illnessCreateRequestDto) {
        Illness newIllnessObject = Illness.builder()
                .name(illnessCreateRequestDto.getName())
                .description(illnessCreateRequestDto.getDescription())
                .doctorId(illnessCreateRequestDto.getDoctorId())
                .pacientId(illnessCreateRequestDto.getPacientId())
                .tags(illnessCreateRequestDto.getTags())
                .date(illnessCreateRequestDto.getDate())
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

        // Perform Symptom Validation - TODO: Break Circuit
        try{
            ResponseEntity<Boolean> symptomExists = restTemplate.getForEntity(SymptomServiceAddress  + "verify?symptomId=" + symptomId, Boolean.class);

            if(!symptomExists.getBody().booleanValue())
                return false;
        } catch (HttpClientErrorException e) {
            System.out.println("Symptom does not exist");
            return false;
        } catch (RestClientException e) {
            System.out.println("Symptom service down");
            // TODO: LOCAL CACHING
            return false;
        } catch (NullPointerException e){
            System.out.println("No body");
        }



        Query query = Query.query(Criteria.where("_id").is(illnessId));
        Update update = new Update().addToSet("symptomsId", symptomId);

        UpdateResult updatedObject = mongoTemplate.updateFirst(query, update, Illness.class);

        return updatedObject.getMatchedCount() > 0;
    }

    public IllnessGetResponseDto getIllnessById(String illnessId) {
        Illness foundIllness = mongoTemplate.findById(illnessId, Illness.class);

        if(foundIllness == null)
            return null;

        IllnessGetResponseDto foundIllnessDto = new IllnessGetResponseDto(
                foundIllness.getId(),
                foundIllness.getName(),
                foundIllness.getDescription(),
                foundIllness.getPacientId(),
                foundIllness.getDoctorId(),
                foundIllness.getPrescriptionId(),
                foundIllness.getSymptomsId(),
                foundIllness.getTags(),
                foundIllness.getDate()
        );

        return  foundIllnessDto;
    }

    public List<IllnessGetResponseDto> getIllneses(String userId) {
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
