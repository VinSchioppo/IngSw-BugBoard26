package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.dto.teammember.*;
import com.ingsw.bugboard26.exception.DuplicateException;
import com.ingsw.bugboard26.exception.NotFoundException;
import com.ingsw.bugboard26.service.teammember.TeamMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;

@RestController
@RequestMapping(value = "api/members")
public class TeamMemberController
{
    private static final Logger logger = LoggerFactory.getLogger(TeamMemberController.class);

    static final String CODE = "code";
    static final String MESSAGE = "message";

    private final TeamMemberService teamMemberService;

    @Autowired
    public TeamMemberController(final TeamMemberService teamMemberService)
    {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<TeamMemberInfoDto>> getAllTeamMembers()
            throws NotFoundException
    {
        logger.info("****** Otteniamo tutti i team members *******");

        List<TeamMemberInfoDto> members = teamMemberService.selectAll();

        if (members.isEmpty())
            throw new NotFoundException("Non sono stati trovati team member.");

        logger.info("Numero dei record: {}.", members.size());

        return new ResponseEntity<List<TeamMemberInfoDto>>(members, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberDto> getMember(@PathVariable("username") String username)
            throws NotFoundException
    {
        logger.info("****** Cerchiamo l'utente *******");

        TeamMemberDto member = teamMemberService.selectByUsername(username);

        if (member == null)
            throw new NotFoundException("L'utente " + username + " non è stato trovato.");

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamMemberDto>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/issues/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberIssuesDto> getMemberIssues(@PathVariable("username") String username)
            throws NotFoundException
    {
        logger.info("****** Cerchiamo le issues dell'utente *******");

        TeamMemberIssuesDto member = teamMemberService.selectIssuesByUsername(username);

        if (member == null)
            throw new NotFoundException("L'utente " + username + " non è stato trovato.");

        logger.info("Issues trovate.");

        return new ResponseEntity<TeamMemberIssuesDto>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/notifications/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberNotificationsDto> getMemberNotifications(@PathVariable("username") String username)
            throws NotFoundException
    {
        logger.info("****** Cerchiamo le notifiche dell'utente *******");

        TeamMemberNotificationsDto member = teamMemberService.selectNotificationsByUsername(username);

        if (member == null)
            throw new NotFoundException("Il nome utente " + username + " è errato.");

        logger.info("Notifiche trovate.");

        return new ResponseEntity<TeamMemberNotificationsDto>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/comments/{username}",produces = "application/json")
    public ResponseEntity<TeamMemberCommentsDto> getMemberComments(@PathVariable("username") String username)
            throws NotFoundException
    {
        logger.info("****** Cerchiamo i commenti dell'utente *******");

        TeamMemberCommentsDto member = teamMemberService.selectCommentsByUsername(username);

        if (member == null)
            throw new NotFoundException("Il nome utente " + username + " è errato.");

        logger.info("Commenti trovati.");

        return new ResponseEntity<TeamMemberCommentsDto>(member, HttpStatus.OK);
    }

    @PutMapping(value = "/signup")
    public ResponseEntity<List<TeamMemberInfoDto>> signUp(@RequestBody List<TeamMemberInfoDto> teamMembers)
            throws DuplicateException
    {
        logger.info("****** Registriamo i nuovi utenti *******");

        teamMemberService.insertTeamMembers(teamMembers);

        logger.info("Utenti registrati con successo.");

        return new ResponseEntity<List<TeamMemberInfoDto>>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/comments/{username}")
    public ResponseEntity<TeamMemberCommentsDto> postComments(
            @PathVariable("username") String username, @RequestBody TeamMemberCommentsDto comments)
    {
        logger.info("****** Inseriamo un commento *******");

        teamMemberService.insertComments(username, comments);

        logger.info("Commento inserito con successo.");

        return new ResponseEntity<TeamMemberCommentsDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/notifications/{username}")
    public ResponseEntity<TeamMemberNotificationsDto> sendNotifications(
            @PathVariable("username") String username, @RequestBody TeamMemberNotificationsDto notifications)
    {
        logger.info("****** Mandiamo la notifica *******");

        teamMemberService.insertNotifications(username, notifications);

        logger.info("Notifica inviata con successo.");

        return new ResponseEntity<TeamMemberNotificationsDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/password/{username}")
    public ResponseEntity<TeamMemberInfoDto> changePassword(
            @PathVariable("username") String username, @RequestBody TeamMemberInfoDto password)
    {
        logger.info("****** Cambiamo la password utente *******");

        teamMemberService.updatePassword(username, password);

        logger.info("Password cambiata con successo.");

        return new ResponseEntity<TeamMemberInfoDto>(new HttpHeaders(), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/promotion")
    public ResponseEntity<List<TeamMemberInfoDto>> promoteMembers(@RequestBody List<TeamMemberInfoDto> teamMembers)
    {
        logger.info("****** Promuoviamo utenti ad admin *******");

        teamMemberService.updateToAdmin(teamMembers);

        logger.info("Utenti promossi con successo.");

        return new ResponseEntity<List<TeamMemberInfoDto>>(new HttpHeaders(), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/notifications/{username}")
    public ResponseEntity<TeamMemberNotificationsDto> changeStatusNotifications(
            @PathVariable("username") String username, @RequestBody TeamMemberNotificationsDto notifications)
    {
        logger.info("****** Aggiorniamo le notifiche *******");

        teamMemberService.updateStatusNotifications(username, notifications);

        logger.info("Notifiche aggiornate con successo.");

        return new ResponseEntity<TeamMemberNotificationsDto>(new HttpHeaders(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> removeMember(@PathVariable("username") String username)
            throws NotFoundException
    {
        logger.info("****** Eliminiamo l'utente con username {} ******", username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        teamMemberService.deleteMember(username);

        responseNode.put(CODE, HttpStatus.OK.toString());
        responseNode.put(MESSAGE, "Eliminazione utente " + username + " eseguita con successo!");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "notifications/{username}")
    public ResponseEntity<Object> removeNotifications(@PathVariable("username") String username)
            throws NotFoundException
    {
        logger.info("****** Eliminiamo le notifiche viste dall'utente con username {} ******", username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        teamMemberService.deleteNotifications(username);

        responseNode.put(CODE, HttpStatus.OK.toString());
        responseNode.put(MESSAGE, "Eliminazione notifiche utente " + username + " eseguita con successo!");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }
}
