package com.GrabbingTheCode.usrmng.server.service;

import com.GrabbingTheCode.usrmng.core.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//Here we are Implementing Interface called UserDetailsService because we want that spring security to use this
//UserService class to verify the user details like if the user is enable or not password is correct or not so on and so
//forth
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("" +
                "No User was found with the specified email"));
    }
}
