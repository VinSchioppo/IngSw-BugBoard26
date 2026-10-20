package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.entities.TeamMember;
import com.ingsw.bugboard26.service.TeamMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/teammember")
public class TeamMemberController
{
    private static final Logger logger = LoggerFactory.getLogger(TeamMemberController.class);

    private final TeamMemberService teamMemberService;

    @Autowired
    public TeamMemberController(final TeamMemberService teamMemberService)
    {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<TeamMember>> listAllTeamMembers()
    {
        logger.info("****** Otteniamo tutti i team members *******");

        List<TeamMember> members = teamMemberService.selectAll();

        if (members.isEmpty())
            return new ResponseEntity<List<TeamMember>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}", members.size());

        return new ResponseEntity<List<TeamMember>>(members, HttpStatus.OK);
    }

    @GetMapping(value = "/username/{username}",produces = "application/json")
    public ResponseEntity<TeamMember> getUserByUsername(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'utente *******");

        TeamMember member = teamMemberService.selectByUserame(username);

        if (member == null)
            return new ResponseEntity<TeamMember>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamMember>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/all",produces = "application/json")
    public ResponseEntity<List<TeamMember>> getAllUserInfo()
    {
        logger.info("****** Cerchiamo tutti gli utenti *******");

        List<TeamMember> members = teamMemberService.selectAllTeamMemberInfo();

        if (members == null)
            return new ResponseEntity<List<TeamMember>>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<List<TeamMember>>(members, HttpStatus.OK);
    }
}
