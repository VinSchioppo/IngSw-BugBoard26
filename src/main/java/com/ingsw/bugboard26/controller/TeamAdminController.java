package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.dto.teamadmin.*;
import com.ingsw.bugboard26.service.teamadmin.TeamAdminService;
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
@RequestMapping(value = "api/admins")
public class TeamAdminController
{
    private static final Logger logger = LoggerFactory.getLogger(TeamAdminController.class);

    static final String CODE = "code";
    static final String MESSAGE = "message";

    private final TeamAdminService teamAdminService;

    @Autowired
    public TeamAdminController(final TeamAdminService teamAdminService)
    {
        this.teamAdminService = teamAdminService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<TeamAdminInfoDto>> listAllTeamAdmin()
    {
        logger.info("****** Otteniamo tutti i team admins *******");

        List<TeamAdminInfoDto> admins = teamAdminService.selectAll();

        if (admins.isEmpty())
            return new ResponseEntity<List<TeamAdminInfoDto>>(HttpStatus.NOT_FOUND);

        logger.info("Numero dei record: {}.", admins.size());

        return new ResponseEntity<List<TeamAdminInfoDto>>(admins, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}",produces = "application/json")
    public ResponseEntity<TeamAdminDto> getAdmin(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'admin *******");

        TeamAdminDto admin = teamAdminService.selectByUsername(username);

        if (admin == null)
            return new ResponseEntity<TeamAdminDto>(HttpStatus.NOT_FOUND);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamAdminDto>(admin, HttpStatus.OK);
    }

    @PutMapping(value = "/notifications/{username}")
    public ResponseEntity<TeamAdminDto> sendNotifications(
            @PathVariable("username") String username, @RequestBody TeamAdminDto notification)
    {
        logger.info("****** Mandiamo la notifica *******");

        if(notification == null)
            return new ResponseEntity<TeamAdminDto>(HttpStatus.NO_CONTENT);

        teamAdminService.insertNotifications(username, notification);

        logger.info("Notifica inviata con successo.");

        return new ResponseEntity<TeamAdminDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/notifications/{username}")
    public ResponseEntity<TeamAdminDto> changeStatusNotifications(
            @PathVariable("username") String username, @RequestBody TeamAdminDto notifications)
    {
        logger.info("****** Mandiamo la notifica *******");

        if(notifications == null)
            return new ResponseEntity<TeamAdminDto>(HttpStatus.NO_CONTENT);

        teamAdminService.updateStatusNotifications(username, notifications);

        logger.info("Notifica aggiornata con successo.");

        return new ResponseEntity<TeamAdminDto>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<?> removeAdmin(@PathVariable("username") String username)
    {
        logger.info("Eliminiamo l'admin con username {}", username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus httpStatus = HttpStatus.OK;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        if(teamAdminService.deleteAdmin(username))
        {
            responseNode.put(CODE, HttpStatus.OK.toString());
            responseNode.put(MESSAGE, "Eliminazione admin " + username + " eseguita con successo!");
        }
        else
        {
            responseNode.put(CODE, HttpStatus.NOT_FOUND.toString());
            responseNode.put(MESSAGE, "Admin non trovata.");
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(responseNode, headers, httpStatus);
    }

    @DeleteMapping(value = "notifications/{username}")
    public ResponseEntity<?> removeNotifications(@PathVariable("username") String username)
    {
        logger.info("Eliminiamo le notifiche viste dall'admin con username {}", username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus httpStatus = HttpStatus.OK;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        if(teamAdminService.deleteNotifications(username))
        {
            responseNode.put(CODE, HttpStatus.OK.toString());
            responseNode.put(MESSAGE, "Eliminazione notifiche admin " + username + " eseguita con successo!");
        }
        else
        {
            responseNode.put(CODE, HttpStatus.NOT_FOUND.toString());
            responseNode.put(MESSAGE, "Notifiche non trovate.");
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(responseNode, headers, httpStatus);
    }
}
