package com.GrabbingTheCode.usrmng.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
