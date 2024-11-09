package com.stackbytes.service;

import com.stackbytes.model.Prescription;
import com.stackbytes.model.dto.PrescriptionCreateResponseDto;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class PrescriptionService {



    private final MongoTemplate mongoTemplate;
    private final MinioClient minioClient;

    public PrescriptionService(MongoTemplate mongoTemplate, MinioClient minioClient) {
        this.mongoTemplate = mongoTemplate;
        this.minioClient = minioClient;
    }


    public PrescriptionCreateResponseDto createPrescription(String illnessId, String doctorId , MultipartFile prescriptionFile) {

        //Get Doctor gpg - cache to redis

        Prescription newPrescription = Prescription.builder()
                .illnessId(illnessId)
                .signature("GPG_SIGNATURE")
                .medicId(doctorId)
                .publicKey("GPG_PUBLIC")
                .build();

        Prescription insertedPrescription = mongoTemplate.insert(newPrescription);


        //Add zipking integration

        if(prescriptionFile.isEmpty()) {
            return null;
        }
        String contentType = prescriptionFile.getContentType();
        if ( contentType == null || !contentType.startsWith("image/")) {
            System.out.println("not an image");
            return null;
        }


        try{
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("prescriptions")
                            .stream(prescriptionFile.getInputStream(), prescriptionFile.getSize(), -1)
                            .contentType(prescriptionFile.getContentType())
                            .object(insertedPrescription.getId())
                            .build()
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


        return new PrescriptionCreateResponseDto(insertedPrescription.getId());
    }
}
