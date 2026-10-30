package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.service.teammember.TeamMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/members")
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
    public ResponseEntity<List<TeamMemberInfoDto>> listAllTeamMembers()
    {
        logger.info("****** Otteniamo tutti i team members *******");

        List<TeamMemberInfoDto> members = teamMemberService.selectAll();

        if (members.isEmpty())
            return new ResponseEntity<List<TeamMemberInfoDto>>(HttpStatus.NO_CONTENT);

        logger.info("Numero dei record: {}.", members.size());

        return new ResponseEntity<List<TeamMemberInfoDto>>(members, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberDto> getUserByUsername(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'utente *******");

        TeamMemberDto member = teamMemberService.selectByUsername(username);

        if (member == null)
            return new ResponseEntity<TeamMemberDto>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamMemberDto>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/issues/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberIssuesDto> getUserIssuesByUsername(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'utente *******");

        TeamMemberIssuesDto member = teamMemberService.selectIssuesByUsername(username);

        if (member == null)
            return new ResponseEntity<TeamMemberIssuesDto>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamMemberIssuesDto>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/notifications/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberNotificationsDto> getUserNotificationsByUsername(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'utente *******");

        TeamMemberNotificationsDto member = teamMemberService.selectNotificationsByUsername(username);

        if (member == null)
            return new ResponseEntity<TeamMemberNotificationsDto>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamMemberNotificationsDto>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/comments/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberCommentsDto> getUserCommentsByUsername(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'utente *******");

        TeamMemberCommentsDto member = teamMemberService.selectCommentsByUsername(username);

        if (member == null)
            return new ResponseEntity<TeamMemberCommentsDto>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamMemberCommentsDto>(member, HttpStatus.OK);
    }

    @PutMapping(value = "/signup")
    public ResponseEntity<List<TeamMemberInfoDto>> signUp(@RequestBody List<TeamMemberInfoDto> teamMembers)
    {
        logger.info("****** Registriamo i nuovi utenti *******");

        if(teamMembers == null)
            return new ResponseEntity<List<TeamMemberInfoDto>>(HttpStatus.NO_CONTENT);

        teamMemberService.insertTeamMembers(teamMembers);

        logger.info("Utenti registrati con successo.");

        return new ResponseEntity<List<TeamMemberInfoDto>>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/comments/{username}")
    public ResponseEntity<TeamMemberCommentsDto> postComments(
            @PathVariable("username") String username, @RequestBody TeamMemberCommentsDto comments)
    {
        logger.info("****** Mandiamo la notifica *******");

        if(comments == null)
            return new ResponseEntity<TeamMemberCommentsDto>(HttpStatus.NO_CONTENT);

        teamMemberService.insertComments(username, comments);

        logger.info("Notifica inviata con successo.");

        return new ResponseEntity<TeamMemberCommentsDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/notifications/{username}")
    public ResponseEntity<TeamMemberNotificationsDto> sendNotification(
            @PathVariable("username") String username, @RequestBody TeamMemberNotificationsDto notifications)
    {
        logger.info("****** Mandiamo la notifica *******");

        if(notifications == null)
            return new ResponseEntity<TeamMemberNotificationsDto>(HttpStatus.NO_CONTENT);

        teamMemberService.insertNotifications(username, notifications);

        logger.info("Notifica inviata con successo.");

        return new ResponseEntity<TeamMemberNotificationsDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/password/{username}")
    public ResponseEntity<TeamMemberInfoDto> changePassword(
            @PathVariable("username") String username, @RequestBody TeamMemberInfoDto password)
    {
        logger.info("****** Cambiamo la password utente *******");

        if(password == null)
            return new ResponseEntity<TeamMemberInfoDto>(HttpStatus.NO_CONTENT);

        teamMemberService.updatePassword(username, password);

        logger.info("Password cambiata con successo.");

        return new ResponseEntity<TeamMemberInfoDto>(new HttpHeaders(), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/promotion")
    public ResponseEntity<List<TeamMemberInfoDto>> promoteMembers(@RequestBody List<TeamMemberInfoDto> teamMembers)
    {
        logger.info("****** Promuoviamo utenti ad admin *******");

        if(teamMembers == null)
            return new ResponseEntity<List<TeamMemberInfoDto>>(HttpStatus.NO_CONTENT);

        teamMemberService.updateToAdmin(teamMembers);

        logger.info("Utenti promossi con successo.");

        return new ResponseEntity<List<TeamMemberInfoDto>>(new HttpHeaders(), HttpStatus.ACCEPTED);
    }
}
