package com.stackbytes.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackbytes.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.HashMap;

@Service
public class UserService {
    private final MongoTemplate mongoTemplate;
    @Autowired
    private GPGKeyGenerator gpgKeyGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private GetProperties getProperties;
    private final ObjectMapper objectMapper;
    public UserService(MongoTemplate mongoTemplate, ObjectMapper objectMapper) {
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper;
    }
    public ResponseJson verifyMedic(Medic medic) {
        ContactInfo contactInfo = medic.getContactInfo();
        Medic findMedic = mongoTemplate.findOne(new Query(Criteria.where("email").is(contactInfo.getEmail())), Medic.class);
        if (findMedic == null) {
            return ResponseJson.builder().code(404).status(false).message("Medic does not exist").build();
        }
        if (findMedic.getGpg() == null) {
            return ResponseJson.builder().code(404).status(false).message("Medic does not have a GPG key").build();
        }
        try {
            HashMap<String, String> gpg = findMedic.getGpg();
            return ResponseJson.builder().code(200).status(true).message("Medic verified").gpg(gpg).build();
        } catch (Exception e) {
            return ResponseJson.builder().code(500).status(false).message("Internal server error").build();
        }
    }
    public String test() {
        HashMap<String, String> response = gpgKeyGenerator.generateKey("test", "test");
        return "public key : " + response.get("publicKey") + "\nprivate key : " + response.get("privateKey") + "\nAESKey : "   + response.get("AESKey");
    }
    public ResponseJson loginUser(LoginData loginData) {
        if (loginData.getEmail() == null || loginData.getPassword() == null) {
            return ResponseJson.builder().code(404).status(false).message("Email or password cannot be null").build();
        }
        try {
          User user = mongoTemplate.findOne(new Query(Criteria.where("email").is(loginData.getEmail())), User.class);
            if (user == null) {
                Medic medic = mongoTemplate.findOne(new Query(Criteria.where("email").is(loginData.getEmail())), Medic.class);
                if (medic == null) {
                    return ResponseJson.builder().code(404).status(false).message("User or medic not found").build();
                } else {
                    if (BCrypt.checkpw(loginData.getPassword(), medic.getPassword())) {
                        String jsonStringTokenContent = objectMapper.writeValueAsString(medic);
                        String token = jwtUtil.getToken(jsonStringTokenContent);
                        return ResponseJson.builder().code(200).status(true).message("Medic logged in").token(token).build();
                    } else {
                        return ResponseJson.builder().code(404).status(false).message("Invalid password").build();
                    }
                }
            } else {
                if (BCrypt.checkpw(loginData.getPassword(), user.getPassword())) {
                    String jsonStringTokenContent = objectMapper.writeValueAsString(user);
                    String token = jwtUtil.getToken(jsonStringTokenContent);
                    return ResponseJson.builder().code(200).status(true).message("User logged in").token(token).build();
                } else {
                    return ResponseJson.builder().code(404).status(false).message("Invalid password").build();
                }
            }
        }  catch (Exception e) {
            return ResponseJson.builder().code(500).status(false).message("Internal server error").build();
        }
    }
    public ResponseJson registerUser(RegisterRequestDto registerRequestDto) {
        if(registerRequestDto == null){
            return ResponseJson.builder().code(404).status(false).message("User cannot be null").build();
        }
        try {
            User user = registerRequestDto.getUser();
            if(user == null) {
                Medic medic = registerRequestDto.getMedic();
                ContactInfo contactInfo = medic.getContactInfo();
                Medic findMedic = mongoTemplate.findOne(new Query(Criteria.where("email").is(contactInfo.getEmail())), Medic.class);
                if (findMedic != null) {
                    return ResponseJson.builder().code(404).status(false).message("Medic already exists").build();
                }
                medic.setPassword(BCrypt.hashpw(medic.getPassword(), BCrypt.gensalt()));
                medic.setCreatedAt(new Date());
                medic.setUpdatedAt(new Date());
                medic.setGpg(gpgKeyGenerator.generateKey(contactInfo.getEmail(), medic.getPassword()));
                try {
                    mongoTemplate.save(medic);
                    return ResponseJson.builder().code(200).status(true).message("Medic registered").build();
                } catch (Exception e) {
                    return ResponseJson.builder().code(500).status(false).message("Internal server error").build();
                }
            } else {
                User findUser = mongoTemplate.findOne(new Query(Criteria.where("email").is(user.getEmail())), User.class);
                if (findUser != null) {
                    return ResponseJson.builder().code(404).status(false).message("User already exists").build();
                }
                user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
                user.setCreatedAt(new Date());
                user.setUpdatedAt(new Date());
                try {
                    mongoTemplate.save(user);
                    return ResponseJson.builder().code(200).status(true).message("User registered").build();
                } catch (Exception e) {
                    return ResponseJson.builder().code(500).status(false).message("Internal server error").build();
                }
            }
        } catch (Exception e) {
            return ResponseJson.builder().code(500).status(false).message("Internal server error").build();
        }
    }
}
