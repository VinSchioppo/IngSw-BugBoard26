package com.ingsw.bugboard26;

import com.ingsw.bugboard26.dto.notification.NotificationTeamAdminDto;
import com.ingsw.bugboard26.dto.teamadmin.TeamAdminDto;
import com.ingsw.bugboard26.dto.teammember.TeamMemberInfoDto;
import com.ingsw.bugboard26.service.teamadmin.TeamAdminService;
import com.ingsw.bugboard26.service.teammember.TeamMemberService;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TeamAdminControllerTest
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    TeamMemberService teamMemberService;

    @Autowired
    TeamAdminService teamAdminService;

    private int nRecords = 0;

    @BeforeEach
    void setUp()
    {
        List<TeamMemberInfoDto> teamMembers = new ArrayList<>();
        teamMembers.add(new TeamMemberInfoDto("Mario", "mario@gmail.com", "Mario123", true));
        teamMembers.add(new TeamMemberInfoDto("Gino", "gino@gmail.com", "Gino123", true));

        List<NotificationTeamAdminDto> notifications = new ArrayList<>();
        notifications.add(new NotificationTeamAdminDto("Mario", "La notifica di Gino."));

        teamMemberService.insertTeamMembers(teamMembers);
        teamAdminService.insertNotifications("Gino", new TeamAdminDto(notifications));

        nRecords = 2;
    }

    @Test
    void getAllTeamAdminTest() throws Exception
    {
        mvc.perform(get("/api/admins")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(nRecords)))
                .andDo(print());
    }

    @Test
    void getAdminTest() throws Exception
    {
        mvc.perform(get("/api/admins/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.notifications[0].memberUsername").value("Mario"))
                .andExpect(jsonPath("$.notifications[0].description").value("La notifica di Gino."))
                .andDo(print());

        mvc.perform(get("/api/admins/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void sendNotificationsTest() throws Exception
    {
        JsonObject notification = new JsonObject();
        notification.addProperty("memberUsername", "Gino");
        notification.addProperty("description", "La notifica di Mario.");

        JsonArray notifications = new JsonArray();
        notifications.add(notification);

        JsonObject teamAdmin = new JsonObject();
        teamAdmin.add("notifications", notifications);

        mvc.perform(put("/api/admins/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        mvc.perform(get("/api/admins/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(1)))
                .andExpect(jsonPath("$.notifications[0].memberUsername").value("Gino"))
                .andExpect(jsonPath("$.notifications[0].description").value("La notifica di Mario."))
                .andDo(print());

        mvc.perform(put("/api/admins/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        notification = new JsonObject();
        notification.addProperty("memberUsername", "Gino");
        notification.addProperty("description", "La notifica di Mario 2.");

        notifications = new JsonArray();
        notifications.add(notification);
        notifications.add(notification);

        teamAdmin = new JsonObject();
        teamAdmin.add("notifications", notifications);

        mvc.perform(put("/api/admins/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        mvc.perform(put("/api/admins/notifications/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        notification = new JsonObject();
        notification.addProperty("memberUsername", "Gino");

        notifications = new JsonArray();
        notifications.add(notification);
        notifications.add(notification);

        teamAdmin = new JsonObject();
        teamAdmin.add("notifications", notifications);

        mvc.perform(put("/api/admins/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        notification = new JsonObject();
        notification.addProperty("description", "La notifica di Mario 3.");

        notifications = new JsonArray();
        notifications.add(notification);
        notifications.add(notification);

        teamAdmin = new JsonObject();
        teamAdmin.add("notifications", notifications);

        mvc.perform(put("/api/admins/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        teamAdmin = new JsonObject();

        mvc.perform(put("/api/admins/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void changeStatusNotificationsTest() throws Exception
    {
        JsonObject notification = new JsonObject();
        notification.addProperty("memberUsername", "Mario");
        notification.addProperty("description", "La notifica di Gino.");

        JsonArray notifications = new JsonArray();
        notifications.add(notification);

        JsonObject teamAdmin = new JsonObject();
        teamAdmin.add("notifications", notifications);

        mvc.perform(get("/api/admins/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(1)))
                .andDo(print());

        mvc.perform(post("/api/admins/notifications/Gino")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mvc.perform(get("/api/admins/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(0)))
                .andDo(print());

        mvc.perform(post("/api/admins/notifications/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        teamAdmin = new JsonObject();

        mvc.perform(post("/api/admins/notifications/Gino")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void removeAdminTest() throws Exception
    {
        mvc.perform(get("/api/admins/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andDo(print());

        mvc.perform(delete("/api/admins/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(delete("/api/admins/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        mvc.perform(delete("/api/admins/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void removeNotificationsTest() throws Exception
    {
        JsonObject notification = new JsonObject();
        notification.addProperty("memberUsername", "Mario");
        notification.addProperty("description", "La notifica di Gino.");

        JsonArray notifications = new JsonArray();
        notifications.add(notification);

        JsonObject teamAdmin = new JsonObject();
        teamAdmin.add("notifications", notifications);

        mvc.perform(get("/api/admins/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(1)))
                .andExpect(jsonPath("$.notifications[0].memberUsername").value("Mario"))
                .andExpect(jsonPath("$.notifications[0].description").value("La notifica di Gino."))
                .andDo(print());

        mvc.perform(post("/api/admins/notifications/Gino")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamAdmin.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mvc.perform(delete("/api/admins/notifications/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(get("/api/admins/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(0)))
                .andDo(print());

        mvc.perform(delete("/api/admins/notifications/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
