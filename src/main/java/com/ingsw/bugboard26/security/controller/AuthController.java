package com.ingsw.bugboard26.security.controller;

import com.ingsw.bugboard26.security.model.AuthRequest;
import com.ingsw.bugboard26.security.model.AuthResponse;
import com.ingsw.bugboard26.security.service.auth.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @Autowired
    AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest)
    {
        logger.info("****** Verifichiamo le informazioni di login *******");

        return authService.authenticate(authRequest);
    }
}
