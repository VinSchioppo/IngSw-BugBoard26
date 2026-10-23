package com.ingsw.bugboard26.config;

import com.ingsw.bugboard26.dto.issue.IssueInfoDto;
import com.ingsw.bugboard26.dto.teammember.TeamMemberInfoDto;
import com.ingsw.bugboard26.entities.Issue;
import com.ingsw.bugboard26.entities.TeamMember;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig
{
    @Bean
    ModelMapper modelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(TeamMember.class, TeamMemberInfoDto.class)
                .addMappings(m -> m.skip(TeamMemberInfoDto::setAdmin));
        modelMapper.typeMap(Issue.class, IssueInfoDto.class)
                .addMappings(m -> m.map(
                        source -> source.getTeamMember().getUsername(), IssueInfoDto::setMemberUsername));
        return modelMapper;
    }
}
