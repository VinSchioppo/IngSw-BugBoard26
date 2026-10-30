package com.ingsw.bugboard26.security.service.authuserdetails;

import com.ingsw.bugboard26.repository.TeamMemberRepository;
import com.ingsw.bugboard26.security.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsServiceImpl implements AuthUserDetailsService, UserDetailsService
{
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    public AuthUserDetailsServiceImpl(final TeamMemberRepository teamMemberRepository)
    {
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        var teamMember = teamMemberRepository.findByUsername(username);
        if (teamMember != null)
        {
            return new AuthUser(teamMember);
        }
        else
            throw new UsernameNotFoundException("User not found: " + username);
    }
}
