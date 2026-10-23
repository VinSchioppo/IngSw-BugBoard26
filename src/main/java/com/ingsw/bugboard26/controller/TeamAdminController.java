package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.dto.teamadmin.*;
import com.ingsw.bugboard26.service.teamadmin.TeamAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/admin")
public class TeamAdminController
{
    private static final Logger logger = LoggerFactory.getLogger(TeamAdminController.class);

    private final TeamAdminService teamAdminService;

    @Autowired
    public TeamAdminController(final TeamAdminService teamAdminService)
    {
        this.teamAdminService = teamAdminService;
    }

    @GetMapping(value = "/username/{username}",produces = "application/json")
    public ResponseEntity<TeamAdminDto> getAdmin(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'admin *******");

        TeamAdminDto admin = teamAdminService.selectByUserame(username);

        if (admin == null)
            return new ResponseEntity<TeamAdminDto>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamAdminDto>(admin, HttpStatus.OK);
    }

    @PostMapping(value = "/sendnotifications/username/{username}")
    public ResponseEntity<TeamAdminDto> sendNotification(
            @PathVariable("username") String username, @RequestBody TeamAdminDto notification)
    {
        logger.info("****** Mandiamo la notifica *******");

        if(notification == null)
            return new ResponseEntity<TeamAdminDto>(HttpStatus.NO_CONTENT);

        teamAdminService.insertNotifications(username, notification);

        logger.info("****** Notifica inviata con successo. *******");

        return new ResponseEntity<TeamAdminDto>(new HttpHeaders(), HttpStatus.CREATED);
    }
}
