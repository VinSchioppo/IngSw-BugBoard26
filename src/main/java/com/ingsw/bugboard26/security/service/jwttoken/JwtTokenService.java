package com.ingsw.bugboard26.security.service.jwttoken;

import org.springframework.security.core.Authentication;

public interface JwtTokenService
{
    String generateToken(Authentication authentication, String role);
    Long extractExpirationTime(String token);
}
