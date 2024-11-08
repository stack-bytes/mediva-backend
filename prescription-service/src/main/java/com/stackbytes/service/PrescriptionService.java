package com.stackbytes.service;

import com.stackbytes.model.Prescription;
import com.stackbytes.model.dto.PrescriptionCreateRequestDto;
import com.stackbytes.model.dto.PrescriptionCreateResponseDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    private final MongoTemplate mongoTemplate;

    public PrescriptionService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public PrescriptionCreateResponseDto createPrescription(PrescriptionCreateRequestDto prescriptionCreateRequestDto) {

        return null;
    }
}
