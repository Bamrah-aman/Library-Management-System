package com.GrabbingTheCode.usrmng.server.service;

import com.GrabbingTheCode.usrmng.core.dto.RegistrationDTO;
import com.GrabbingTheCode.usrmng.core.entity.ApplicationUser;
import com.GrabbingTheCode.usrmng.core.entity.Token;
import com.GrabbingTheCode.usrmng.core.entity.UserRole;
import com.GrabbingTheCode.usrmng.core.repo.TokenRepo;
import com.GrabbingTheCode.usrmng.core.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepo tokenRepo;

    private final EmailService emailService;

    private static final String CONFIRMATION_URL = "http://localhost:8081/api/v1/authentication/confirm?token=%s";

    @Transactional
    public String register(RegistrationDTO registrationDTO) throws MessagingException {
        //Checking If user Exists or not
        boolean userExists = userRepo.findByEmail(registrationDTO.getEmail()).isPresent();
        //If Exists throw Exception
        if(userExists) {
            throw new IllegalStateException("User Already Exists");
        }
        //Encoding the password here
        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());

        //If not exists just create the new user
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName(registrationDTO.getFirstName())
                .lastName(registrationDTO.getLastName())
                .password(encodedPassword)
                .email(registrationDTO.getEmail())
                .role(UserRole.ROLE_USER)
                .build();

        //And finally save the user
        userRepo.save(applicationUser);

        //Let's generate the token now and associate with the user saved
        String generateToken = UUID.randomUUID().toString();
        Token token = Token.builder()
                .token(generateToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .applicationUser(applicationUser)
                .build();
        //Saving the token also
        tokenRepo.save(token);

        //Send the confirmation email
        emailService.send(registrationDTO.getEmail(), null,
                registrationDTO.getFirstName(), String.format(CONFIRMATION_URL, generateToken));

        return "Token is: "+generateToken;
    }

    @Transactional
    public String confirmAccount(String token) throws MessagingException {
        Token tokenFound = tokenRepo.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
        if(LocalDateTime.now().isAfter(tokenFound.getExpiresAt())) {
            //Then we will generate a new token again
            String generateToken = UUID.randomUUID().toString();
            Token newGeneratedToken = Token.builder()
                    .token(generateToken)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(10))
                    .applicationUser(tokenFound.getApplicationUser())
                    .build();
            tokenRepo.save(newGeneratedToken);

            //Send the token again
            emailService.send(tokenFound.getApplicationUser().getEmail(), null,
                    tokenFound.getApplicationUser().getFirstName(),
                    String.format(CONFIRMATION_URL, generateToken));
            return "New Token has been sent to your registered email";
        }
        tokenFound.getApplicationUser().setEnabled(true);
        tokenRepo.save(tokenFound);
        return "<h1>Your account has been successfully activated</h1>";

    }
}
