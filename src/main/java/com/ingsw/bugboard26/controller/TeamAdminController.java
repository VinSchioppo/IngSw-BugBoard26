package com.ingsw.bugboard26.controller;

import com.ingsw.bugboard26.entities.TeamAdmin;
import com.ingsw.bugboard26.service.TeamAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<TeamAdmin> getAdmin(@PathVariable("username") String username)
    {
        logger.info("****** Cerchiamo l'admin *******");

        TeamAdmin admin = teamAdminService.selectByUserame(username);

        if (admin == null)
            return new ResponseEntity<TeamAdmin>(HttpStatus.NO_CONTENT);

        logger.info("Utente trovato.");

        return new ResponseEntity<TeamAdmin>(admin, HttpStatus.OK);
    }
}
