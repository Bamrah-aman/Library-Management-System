package com.GrabbingTheCode.usrmng.server.controller;

import com.GrabbingTheCode.usrmng.core.dto.RegistrationDTO;
import com.GrabbingTheCode.usrmng.server.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDTO registrationDTO) throws MessagingException {
        String status = registrationService.register(registrationDTO);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam String token) throws MessagingException {
        return ResponseEntity.ok(registrationService.confirmAccount(token));
    }
}

