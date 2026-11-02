package com.ingsw.bugboard26.security.service.auth;

import com.ingsw.bugboard26.security.model.AuthRequest;
import com.ingsw.bugboard26.security.model.AuthResponse;
import com.ingsw.bugboard26.security.service.jwttoken.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService
{
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public AuthServiceImpl(final AuthenticationManager authenticationManager,
                           final JwtTokenService jwtTokenService)
    {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    public AuthResponse authenticate(AuthRequest authRequest)
    {
        var token = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        String jwtToken = jwtTokenService.generateToken(authentication);
        Long expiresAt = jwtTokenService.extractExpirationTime(jwtToken);

        return new AuthResponse(jwtToken, authentication.getName(), expiresAt);
    }
}