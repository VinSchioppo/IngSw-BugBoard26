package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.dto.teammember.*;
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

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<TeamMemberInfoDto>> listAllTeamMembers()
    {
        logger.info("****** Otteniamo tutti i team members *******");

        List<TeamMemberInfoDto> members = teamMemberService.selectAll();

        if (members.isEmpty())
            return new ResponseEntity<List<TeamMemberInfoDto>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}", members.size());

        return new ResponseEntity<List<TeamMemberInfoDto>>(members, HttpStatus.OK);
    }

    @GetMapping(value = "/username/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberDto> getUserByUsername(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'utente *******");

        TeamMemberDto member = teamMemberService.selectByUsername(username);

        if (member == null)
            return new ResponseEntity<TeamMemberDto>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamMemberDto>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/issues/username/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberIssuesDto> getUserIssuesByUsername(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'utente *******");

        TeamMemberIssuesDto member = teamMemberService.selectIssuesByUsername(username);

        if (member == null)
            return new ResponseEntity<TeamMemberIssuesDto>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamMemberIssuesDto>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/notifications/username/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberNotificationsDto> getUserNotificationsByUsername(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'utente *******");

        TeamMemberNotificationsDto member = teamMemberService.selectNotificationsByUsername(username);

        if (member == null)
            return new ResponseEntity<TeamMemberNotificationsDto>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamMemberNotificationsDto>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/comments/username/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberCommentsDto> getUserCommentsByUsername(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'utente *******");

        TeamMemberCommentsDto member = teamMemberService.selectCommentsByUsername(username);

        if (member == null)
            return new ResponseEntity<TeamMemberCommentsDto>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamMemberCommentsDto>(member, HttpStatus.OK);
    }
}
