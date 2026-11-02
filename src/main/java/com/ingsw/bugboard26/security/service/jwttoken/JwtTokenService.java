package com.ingsw.bugboard26.security.service.jwttoken;

import org.springframework.security.core.Authentication;

public interface JwtTokenService
{
    String generateToken(Authentication authentication);
    Long extractExpirationTime(String token);
}
