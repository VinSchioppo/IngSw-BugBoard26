package com.ingsw.bugboard26.security.service.jwttoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenServiceImpl implements JwtTokenService
{
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    @Autowired
    public JwtTokenServiceImpl(final JwtEncoder encoder, final JwtDecoder decoder)
    {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public String generateToken(Authentication authentication, String role)
    {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("role", role)
                .build();
        var encoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.encoder.encode(encoderParameters).getTokenValue();
    }

    public Long extractExpirationTime(String token)
    {
        Jwt jwt = decoder.decode(token);
        var exp = (Instant) jwt.getClaim("exp");
        assert exp != null;
        return exp.toEpochMilli();
    }
}
