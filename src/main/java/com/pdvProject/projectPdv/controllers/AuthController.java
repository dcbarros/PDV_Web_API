package com.pdvProject.projectPdv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdvProject.projectPdv.payload.requests.LoginRequest;
import com.pdvProject.projectPdv.payload.requests.SignupRequest;
import com.pdvProject.projectPdv.services.AuthenticationService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge=3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationService _authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return _authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        return _authenticationService.registerUser(signupRequest);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser(){
        return _authenticationService.logoutUser();
    }
}
