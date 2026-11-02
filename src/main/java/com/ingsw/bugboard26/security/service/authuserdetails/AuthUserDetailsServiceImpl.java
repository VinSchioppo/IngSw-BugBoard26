package com.ingsw.bugboard26.security.service.authuserdetails;

import com.ingsw.bugboard26.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthUserDetailsServiceImpl implements UserDetailsService
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
            List<GrantedAuthority> roles = new ArrayList<>();

            if(teamMember.getAdminAccount() == null)
                roles.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
            else
                roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

            return User.builder()
                    .username(teamMember.getUsername())
                    .password(teamMember.getPassword())
                    .authorities(roles)
                    .build();
        }
        else
            throw new UsernameNotFoundException("User not found: " + username);
    }
}
