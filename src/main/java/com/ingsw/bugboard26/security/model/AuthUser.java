package com.ingsw.bugboard26.security.model;

import com.ingsw.bugboard26.entities.TeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class AuthUser implements UserDetails
{
    private final TeamMember teamMember;

    @Override
    public String getUsername()
    {
        return teamMember.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of();
    }

    @Override
    public String getPassword()
    {
        return teamMember.getPassword();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
