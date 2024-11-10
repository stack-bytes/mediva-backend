package com.stackbytes.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.stackbytes.models.*;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
public class UserService {
    private final MongoTemplate mongoTemplate;
    @Autowired
    private GPGKeyGenerator2 gpgKeyGenerator2;
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
            Pair<String,String> gpg = medic.getGpg();
            return ResponseJson.builder().code(200).status(true).message("Medic verified").gpg(gpg).build();
        } catch (Exception e) {
            return ResponseJson.builder().code(500).status(false).message("Internal server error").build();
        }
    }
    public String test() throws Exception{
        PGPKeyRingGenerator keyGenerator = gpgKeyGenerator2.generateKey("rares");
        byte[] publicKey = gpgKeyGenerator2.getPublicKeyBytes(keyGenerator);
        byte[] privateKey = gpgKeyGenerator2.getPrivateKeyBytes(keyGenerator);
        return "public key : " + Base64.getEncoder().encodeToString(publicKey) + "\nprivate key : " + Base64.getEncoder().encodeToString(privateKey);
    }
    public ResponseJson loginUser(LoginData loginData) {
        if (loginData.getEmail() == null || loginData.getPassword() == null) {
            return ResponseJson.builder().code(404).status(false).message("Email or password cannot be null").build();
        }
        if(loginData.isMedic()) {
            Medic medic = mongoTemplate.findOne(new Query(Criteria.where("contactInfo.email").is(loginData.getEmail())), Medic.class);
            if (medic == null) {
                return ResponseJson.builder().code(404).status(false).message("Medic not found").build();
            }
            if (BCrypt.checkpw(loginData.getPassword(), medic.getPassword())) {
                String jsonStringTokenContent = null;
                try {
                    jsonStringTokenContent = objectMapper.writeValueAsString(medic);
                } catch (Exception e) {
                    return ResponseJson.builder().code(500).status(false).message(e.getMessage()).build();
                }
                String token = jwtUtil.getToken(jsonStringTokenContent);
                return ResponseJson.builder().code(200).status(true).message("Medic logged in").token(token).build();
            } else {
                return ResponseJson.builder().code(404).status(false).message("Invalid password").build();
            }
        } else {
            try {
            User user = mongoTemplate.findOne(new Query(Criteria.where("email").is(loginData.getEmail())), User.class);
            if (user == null) {
                return ResponseJson.builder().code(404).status(false).message("User not found").build();
            }
            if (BCrypt.checkpw(loginData.getPassword(), user.getPassword())) {
                String jsonStringTokenContent = null;
                try {
                    jsonStringTokenContent = objectMapper.writeValueAsString(user);
                } catch (Exception e) {
                    return ResponseJson.builder().code(500).status(false).message(e.getMessage()).build();
                }
                String token = jwtUtil.getToken(jsonStringTokenContent);
                return ResponseJson.builder().code(200).status(true).message("User logged in").token(token).build();
            } else {
                return ResponseJson.builder().code(404).status(false).message("Invalid password").build();
            }
            } catch (Exception e) {
                return ResponseJson.builder().code(500).status(false).message(e.getMessage()).build();
            }
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
                Medic findMedic = mongoTemplate.findOne(new Query(Criteria.where("contactInfo.email").is(contactInfo.getEmail())), Medic.class);
                if (findMedic != null) {
                    return ResponseJson.builder().code(404).status(false).message("Medic already exists").build();
                }
                    medic.setPassword(BCrypt.hashpw(medic.getPassword(), BCrypt.gensalt()));
                    medic.setCreatedAt(new Date());
                    medic.setUpdatedAt(new Date());
                    PGPKeyRingGenerator keyGenerator = gpgKeyGenerator2.generateKey(contactInfo.getEmail());
                    String publicKey = gpgKeyGenerator2.getPublicKeyBytes(keyGenerator).toString();
                    String privateKey = gpgKeyGenerator2.getPrivateKeyBytes(keyGenerator).toString();
                    String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getBytes());
                    String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getBytes());
                    Pair<String,String> gpg = Pair.of(publicKeyString,privateKeyString);
                    medic.setGpg(gpg);
                try {
                    mongoTemplate.insert(medic);
                    return ResponseJson.builder().code(200).status(true).message("Medic registered").build();
                } catch (Exception e) {
                    return ResponseJson.builder().code(500).status(false).message(e.getMessage()).build();
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
