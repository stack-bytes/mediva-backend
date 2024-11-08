package com.stackbytes.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.Properties;

@Configuration
public class GetProperties {
    public String getProperties(String key) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("users-service/src/main/resources/application.properties"));
            String DB_URL = prop.getProperty(key);
            return DB_URL;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
