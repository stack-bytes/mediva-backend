package com.stackbytes.service;

import com.stackbytes.model.PrescriptionCreateRequestDto;
import com.stackbytes.model.PrescriptionCreateResponseDto;
import io.minio.MinioClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {



    private final MongoTemplate mongoTemplate;
    private final MinioClient minioClient;

    public PrescriptionService(MongoTemplate mongoTemplate, MinioClient minioClient) {
        this.mongoTemplate = mongoTemplate;
        this.minioClient = minioClient;
    }


    public PrescriptionCreateResponseDto createPrescription(PrescriptionCreateRequestDto prescriptionCreateRequestDto) {

        //Get Doctor gpg - cache to redis




        return null;
    }
}
