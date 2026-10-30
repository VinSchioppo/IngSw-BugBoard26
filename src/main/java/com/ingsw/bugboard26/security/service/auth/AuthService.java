package com.ingsw.bugboard26.security.service.auth;

import com.ingsw.bugboard26.security.model.AuthRequest;
import com.ingsw.bugboard26.security.model.AuthResponse;

public interface AuthService
{
    AuthResponse authenticate(AuthRequest authRequest);
}
