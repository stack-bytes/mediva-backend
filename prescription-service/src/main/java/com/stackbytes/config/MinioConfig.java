package com.stackbytes.config;

import io.minio.MinioClient;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://172.18.0.2:9000")
                .credentials("root", "~20,0Evsn?'y>_G^+NmB#?-j%") //TODO: regen secret key
                .build();

    }
}