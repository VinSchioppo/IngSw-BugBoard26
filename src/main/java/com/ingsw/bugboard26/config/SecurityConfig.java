package com.ingsw.bugboard26.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    private static final String ADMIN = "ADMIN";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
    {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/**").permitAll();
                    auth.requestMatchers("/api/admins/**").hasRole(ADMIN);
                    auth.requestMatchers(HttpMethod.PUT,"/api/members/signup", "/api/projects").hasRole(ADMIN);
                    auth.requestMatchers(HttpMethod.POST,"/api/members/promotion", "/api/issues/member/").hasRole(ADMIN);
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 ->
                    oauth2.jwt(withDefaults()))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authManager(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
    {
        var authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
}
