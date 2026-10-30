package com.ingsw.bugboard26.security.service.authuserdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthUserDetailsService
{
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
