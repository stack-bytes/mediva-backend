package com.stackbytes.controllers;

import com.stackbytes.models.LoginData;
import com.stackbytes.models.ResponseJson;
import com.stackbytes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stackbytes.models.User;

@RestController()
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @CrossOrigin
    @PostMapping("/login")
    public ResponseJson loginUser(@RequestBody LoginData loginData) {
        return userService.loginUser(loginData);
    }
    @CrossOrigin
    @PostMapping("/register")
    public ResponseJson registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
    @CrossOrigin
    @GetMapping("/test")
    public String test() {
        return userService.test();
    }
}
