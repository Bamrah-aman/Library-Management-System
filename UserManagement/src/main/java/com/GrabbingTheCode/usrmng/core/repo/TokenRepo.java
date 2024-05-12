package com.GrabbingTheCode.usrmng.core.repo;

import com.GrabbingTheCode.usrmng.core.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);
}
