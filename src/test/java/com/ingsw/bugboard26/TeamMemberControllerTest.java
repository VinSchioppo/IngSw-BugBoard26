package com.ingsw.bugboard26;

import com.ingsw.bugboard26.dto.issue.IssueInfoDto;
import com.ingsw.bugboard26.dto.issue.IssueMemberDto;
import com.ingsw.bugboard26.dto.issuecomment.IssueCommentMemberDto;
import com.ingsw.bugboard26.dto.notification.NotificationTeamMemberDto;
import com.ingsw.bugboard26.dto.project.ProjectDto;
import com.ingsw.bugboard26.dto.project.ProjectIssuesDto;
import com.ingsw.bugboard26.dto.teammember.TeamMemberCommentsDto;
import com.ingsw.bugboard26.dto.teammember.TeamMemberInfoDto;
import com.ingsw.bugboard26.dto.teammember.TeamMemberNotificationsDto;
import com.ingsw.bugboard26.service.issue.IssueService;
import com.ingsw.bugboard26.service.project.ProjectService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TeamMemberControllerTest
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    TeamMemberService teamMemberService;

    @Autowired
    ProjectService projectService;

    @Autowired
    IssueService issueService;

    private int nRecords = 0;

    @BeforeEach
    void setUp()
    {
        List<TeamMemberInfoDto> teamMembers = new ArrayList<>();
        teamMembers.add(new TeamMemberInfoDto("Mario", "mario@gmail.com", "Mario123", true));
        teamMembers.add(new TeamMemberInfoDto("Gino", "gino@gmail.com", "Gino123", true));
        teamMembers.add(new TeamMemberInfoDto("Giulio", "giulio@gmail.com", "Giulio123", false));

        List<IssueInfoDto> issues = new ArrayList<>();
        issues.add(new IssueInfoDto(1L, "Gino", "Issue Gino", "La issue di Gino.",
                1, false, LocalDate.now().plusDays(14), null, null));

        List<IssueCommentMemberDto> comments = new ArrayList<>();
        comments.add(new IssueCommentMemberDto(1L, "Il commento di Gino.", null));

        List<NotificationTeamMemberDto> notifications = new ArrayList<>();
        notifications.add(new NotificationTeamMemberDto("Mario", "La notifica di Gino."));

        teamMemberService.insertTeamMembers(teamMembers);

        projectService.insertProject(new ProjectDto("Project", "Descrizione progetto."));
        projectService.insertIssues(1L, new ProjectIssuesDto(null, issues));

        issueService.insertMember(1L, new IssueMemberDto(teamMembers.get(1)));

        teamMemberService.insertComments("Gino", new TeamMemberCommentsDto(comments));
        teamMemberService.insertNotifications("Gino", new TeamMemberNotificationsDto(notifications));

        nRecords = 3;
    }

    @Test
    void getAllTeamMemberTest() throws Exception
    {
        mvc.perform(get("/api/members")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(nRecords)))
                .andDo(print());
    }

    @Test
    void getMemberTest() throws Exception
    {
        mvc.perform(get("/api/members/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("email").value("mario@gmail.com"))
                .andDo(print());

        mvc.perform(get("/api/members/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getMemberIssuesTest() throws Exception
    {
        mvc.perform(get("/api/members/issues/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.issues", hasSize(0)))
                .andDo(print());

        mvc.perform(get("/api/members/issues/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.issues", hasSize(1)))
                .andExpect(jsonPath("$.issues[0].idIssue").value(1))
                .andExpect(jsonPath("$.issues[0].title").value("Issue Gino"))
                .andExpect(jsonPath("$.issues[0].description").value("La issue di Gino."))
                .andExpect(jsonPath("$.issues[0].priority").value(1))
                .andExpect(jsonPath("$.issues[0].status").value(false))
                .andExpect(jsonPath("$.issues[0].dueDate").value(LocalDate.now().plusDays(14).toString()))
                .andDo(print());

        mvc.perform(get("/api/members/issues/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getMemberNotificationsTest() throws Exception
    {
        mvc.perform(get("/api/members/notifications/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(0)))
                .andDo(print());

        mvc.perform(get("/api/members/notifications/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(1)))
                .andExpect(jsonPath("$.notifications[0].description").value("La notifica di Gino."))
                .andDo(print());

        mvc.perform(get("/api/members/notifications/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getMemberCommentsTest() throws Exception
    {
        mvc.perform(get("/api/members/comments/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.comments", hasSize(0)))
                .andDo(print());

        mvc.perform(get("/api/members/comments/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.comments", hasSize(1)))
                .andExpect(jsonPath("$.comments[0].description").value("Il commento di Gino."))
                .andExpect(jsonPath("$.comments[0].date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.comments[0].idIssue").value(1))
                .andDo(print());

        mvc.perform(get("/api/members/comments/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void signUpTest() throws Exception
    {
        JsonObject teamMember = new JsonObject();
        teamMember.addProperty("username", "Alfredo");
        teamMember.addProperty("email", "alfredo@gmail.com");
        teamMember.addProperty("password", "Alfredo123");
        teamMember.addProperty("isAdmin", "false");

        JsonArray teamMembers = new JsonArray();
        teamMembers.add(teamMember);

        mvc.perform(put("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMembers.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        mvc.perform(get("/api/members/Alfredo")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("email").value("alfredo@gmail.com"))
                .andDo(print());

        mvc.perform(put("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMembers.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        teamMember = new JsonObject();
        teamMember.addProperty("username", "Gianmarco");
        teamMember.addProperty("email", "gianmarco@gmail.com");
        teamMember.addProperty("password", "Gianmarco123");
        teamMember.addProperty("isAdmin", "false");

        teamMembers = new JsonArray();
        teamMembers.add(teamMember);
        teamMembers.add(teamMember);

        mvc.perform(put("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMembers.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        teamMember = new JsonObject();
        teamMember.addProperty("email", "giulio@gmail.com");
        teamMember.addProperty("password", "Giulio123");

        teamMembers = new JsonArray();
        teamMembers.add(teamMember);

        mvc.perform(put("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMembers.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        teamMember = new JsonObject();
        teamMember.addProperty("username", "Giulio");
        teamMember.addProperty("password", "Giulio123");

        teamMembers = new JsonArray();
        teamMembers.add(teamMember);

        mvc.perform(put("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMembers.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        teamMember = new JsonObject();
        teamMember.addProperty("username", "Giulio");
        teamMember.addProperty("email", "giulio@gmail.com");

        teamMembers = new JsonArray();
        teamMembers.add(teamMember);

        mvc.perform(put("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMembers.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        teamMembers = new JsonArray();

        mvc.perform(put("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMembers.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void postCommentsTest() throws Exception
    {
        JsonObject comment = new JsonObject();
        comment.addProperty("idIssue", 1);
        comment.addProperty("description", "Il commento di Mario.");

        JsonArray comments = new JsonArray();
        comments.add(comment);

        JsonObject teamMember = new JsonObject();
        teamMember.add("comments", comments);

        mvc.perform(put("/api/members/comments/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        mvc.perform(get("/api/members/comments/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.comments", hasSize(1)))
                .andExpect(jsonPath("$.comments[0].description").value("Il commento di Mario."))
                .andExpect(jsonPath("$.comments[0].date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.comments[0].idIssue").value(1))
                .andDo(print());

        mvc.perform(put("/api/members/comments/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        comment = new JsonObject();
        comment.addProperty("idIssue", 1);
        comment.addProperty("description", "Il commento di Mario 2.");

        comments = new JsonArray();
        comments.add(comment);
        comments.add(comment);

        teamMember = new JsonObject();
        teamMember.add("comments", comments);

        mvc.perform(put("/api/members/comments/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        mvc.perform(put("/api/members/comments/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        comment = new JsonObject();
        comment.addProperty("idIssue", 1);

        comments = new JsonArray();
        comments.add(comment);

        teamMember = new JsonObject();
        teamMember.add("comments", comments);

        mvc.perform(put("/api/members/comments/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        comment = new JsonObject();
        comment.addProperty("description", "Il commento di Mario 3.");

        comments = new JsonArray();
        comments.add(comment);

        teamMember = new JsonObject();
        teamMember.add("comments", comments);

        mvc.perform(put("/api/members/comments/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        teamMember = new JsonObject();

        mvc.perform(put("/api/members/comments/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void sendNotificationsTest() throws Exception
    {
        JsonObject notification = new JsonObject();
        notification.addProperty("adminUsername", "Gino");
        notification.addProperty("description", "La notifica di Mario.");

        JsonArray notifications = new JsonArray();
        notifications.add(notification);

        JsonObject teamMember = new JsonObject();
        teamMember.add("notifications", notifications);

        mvc.perform(put("/api/members/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        mvc.perform(get("/api/members/notifications/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(1)))
                .andExpect(jsonPath("$.notifications[0].adminUsername").value("Gino"))
                .andExpect(jsonPath("$.notifications[0].description").value("La notifica di Mario."))
                .andDo(print());

        mvc.perform(put("/api/members/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        notification = new JsonObject();
        notification.addProperty("adminUsername", "Gino");
        notification.addProperty("description", "La notifica di Mario 2.");

        notifications = new JsonArray();
        notifications.add(notification);
        notifications.add(notification);

        teamMember = new JsonObject();
        teamMember.add("notifications", notifications);

        mvc.perform(put("/api/members/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        mvc.perform(put("/api/members/notifications/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        notification = new JsonObject();
        notification.addProperty("adminUsername", "Gino");

        notifications = new JsonArray();
        notifications.add(notification);

        teamMember = new JsonObject();
        teamMember.add("notifications", notifications);

        mvc.perform(put("/api/members/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        notification = new JsonObject();
        notification.addProperty("description", "La notifica di Mario 3.");

        notifications = new JsonArray();
        notifications.add(notification);

        teamMember = new JsonObject();
        teamMember.add("notifications", notifications);

        mvc.perform(put("/api/members/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        teamMember = new JsonObject();

        mvc.perform(put("/api/members/notifications/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void changePasswordTest() throws Exception
    {
        JsonObject teamMember = new JsonObject();
        teamMember.addProperty("password", "Mario456");

        mvc.perform(post("/api/members/password/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mvc.perform(post("/api/members/password/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        teamMember = new JsonObject();
        teamMember.addProperty("username", "Mario");

        mvc.perform(post("/api/members/password/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        teamMember = new JsonObject();

        mvc.perform(post("/api/members/password/Mario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void promoteMembersTest() throws Exception
    {
        JsonObject teamMember = new JsonObject();
        teamMember.addProperty("username", "Giulio");

        JsonArray teamMembers = new JsonArray();
        teamMembers.add(teamMember);

        mvc.perform(get("/api/admins/Giulio")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        mvc.perform(post("/api/members/promotion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMembers.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mvc.perform(get("/api/admins/Giulio")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        teamMember = new JsonObject();
        teamMember.addProperty("email", "giulio@gmail.com");

        teamMembers = new JsonArray();
        teamMembers.add(teamMember);

        mvc.perform(post("/api/members/promotion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMembers.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        teamMembers = new JsonArray();

        mvc.perform(post("/api/members/promotion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMembers.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void changeStatusNotificationsTest() throws Exception
    {
        JsonObject notification = new JsonObject();
        notification.addProperty("adminUsername", "Mario");
        notification.addProperty("description", "La notifica di Gino.");

        JsonArray notifications = new JsonArray();
        notifications.add(notification);

        JsonObject teamMember = new JsonObject();
        teamMember.add("notifications", notifications);

        mvc.perform(get("/api/members/notifications/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(1)))
                .andDo(print());

        mvc.perform(post("/api/members/notifications/Gino")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mvc.perform(get("/api/members/notifications/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(0)))
                .andDo(print());

        mvc.perform(post("/api/members/notifications/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        notification = new JsonObject();
        notification.addProperty("adminUsername", "Mario");

        notifications = new JsonArray();
        notifications.add(notification);

        teamMember = new JsonObject();
        teamMember.add("notifications", notifications);

        mvc.perform(post("/api/members/notifications/Gino")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        notification = new JsonObject();
        notification.addProperty("description", "La notifica di Gino.");

        notifications = new JsonArray();
        notifications.add(notification);

        teamMember = new JsonObject();
        teamMember.add("notifications", notifications);

        mvc.perform(post("/api/members/notifications/Gino")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        teamMember = new JsonObject();

        mvc.perform(post("/api/members/notifications/Gino")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void removeMemberTest() throws Exception
    {
        mvc.perform(get("/api/members/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("email").value("mario@gmail.com"))
                .andDo(print());

        mvc.perform(delete("/api/members/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(delete("/api/members/Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        mvc.perform(delete("/api/members/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void removeNotificationsTest() throws Exception
    {
        JsonObject notification = new JsonObject();
        notification.addProperty("adminUsername", "Mario");
        notification.addProperty("description", "La notifica di Gino.");

        JsonArray notifications = new JsonArray();
        notifications.add(notification);

        JsonObject teamMember = new JsonObject();
        teamMember.add("notifications", notifications);

        mvc.perform(get("/api/members/notifications/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(1)))
                .andExpect(jsonPath("$.notifications[0].adminUsername").value("Mario"))
                .andExpect(jsonPath("$.notifications[0].description").value("La notifica di Gino."))
                .andDo(print());

        mvc.perform(post("/api/members/notifications/Gino")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamMember.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mvc.perform(delete("/api/members/notifications/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(get("/api/members/notifications/Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", hasSize(0)))
                .andDo(print());

        mvc.perform(delete("/api/members/notifications/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
