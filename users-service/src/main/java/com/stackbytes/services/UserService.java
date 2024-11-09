package com.stackbytes.services;

import brave.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackbytes.models.LoginData;
import com.stackbytes.models.ResponseJson;
import com.stackbytes.models.User;
import com.stackbytes.models.UsersLoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import org.mindrot.jbcrypt.BCrypt;

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
    public ResponseJson verifyMedic(User user) {
        if(user == null){
            return ResponseJson.builder().code(404).status(false).message("User cannot be null").build();
        }
        try {
            User userExists = mongoTemplate.findOne(new Query(Criteria.where("email").is(user.getEmail())), User.class);
            if (userExists == null) {
                return ResponseJson.builder().code(404).status(false).message("User not found").build();
            }
            if (userExists.isMedic()) {
                HashMap<String,String> gpg = userExists.getGpg();
                return ResponseJson.builder().code(200).status(true).message("User is a medic").gpg(gpg).build();
            } else {
                return ResponseJson.builder().code(404).status(false).message("User is not a medic").build();
            }
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
                return ResponseJson.builder().code(404).status(false).message("User not found").build();
            }
            if (BCrypt.checkpw(loginData.getPassword(), user.getPassword())) {
                UsersLoginResponseDto responseDto = UsersLoginResponseDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .isDoctor(user.isMedic())
                        .fullName(user.getFullName())
                        .build();
                String jsonStringTokenContent = objectMapper.writeValueAsString(responseDto);
                String token = jwtUtil.getToken(jsonStringTokenContent);
                System.out.println(jsonStringTokenContent);
                return ResponseJson.builder()
                        .code(200)
                        .status(true)
                        .message("User logged in")
                        .token(token)
                        .build();
            } else {
                return ResponseJson.builder().code(404).status(false).message("Incorrect password").build();
            }
        } catch (Exception e) {
            return ResponseJson.builder().code(500).status(false).message("Internal server error").build();
        }
    }
    public ResponseJson registerUser(User user){
        if(user == null){
            return ResponseJson.builder().code(404).status(false).message("User cannot be null").build();
        }
        try {
            User userExists = mongoTemplate.findOne(new Query(Criteria.where("email").is(user.getEmail())), User.class);
            if (userExists != null) {
                return ResponseJson.builder().code(404).status(false).message("User already exists").build();
            }
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            try {
                mongoTemplate.save(user);
                return ResponseJson.builder().code(200).status(true).message("User registered").build();
            } catch (Exception e) {
                return ResponseJson.builder().code(500).status(false).message("Internal server error").build();
            }
        } catch (Exception e) {
            return ResponseJson.builder().code(500).status(false).message("Internal server error").build();
        }
    }
}
