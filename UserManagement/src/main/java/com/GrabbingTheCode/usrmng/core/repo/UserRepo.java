package com.GrabbingTheCode.usrmng.core.repo;

import com.GrabbingTheCode.usrmng.core.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<ApplicationUser, Integer> {

    Optional<ApplicationUser> findByEmail(String email);
}
