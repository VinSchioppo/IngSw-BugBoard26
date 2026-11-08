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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
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

    private static boolean isInitialized = false;

    private int nRecords = 0;

    @BeforeEach
    void setUp()
    {
        if (!isInitialized)
        {
            List<TeamMemberInfoDto> teamMembers = new ArrayList<>();
            teamMembers.add(new TeamMemberInfoDto("Mario", "mario@gmail.com", "Mario123", true));
            teamMembers.add(new TeamMemberInfoDto("Gino", "gino@gmail.com", "Gino123", false));

            List<IssueInfoDto> issues = new ArrayList<>();
            issues.add(new IssueInfoDto(1, "Gino", "Issue Gino", "La issue di Gino.",
                    1, false, LocalDate.now().plusDays(14), null, null));

            List<IssueCommentMemberDto> comments = new ArrayList<>();
            comments.add(new IssueCommentMemberDto(1, "Il commento di Gino.", null));

            List<NotificationTeamMemberDto> notifications = new ArrayList<>();
            notifications.add(new NotificationTeamMemberDto("Mario", "La notifica di Gino."));

            teamMemberService.insertTeamMembers(teamMembers);

            projectService.insertProject(new ProjectDto("Project", "Descrizione progetto."));
            projectService.insertIssues(1, new ProjectIssuesDto(null, issues));

            issueService.insertMember(1, new IssueMemberDto(teamMembers.get(1)));

            teamMemberService.insertComments("Gino", new TeamMemberCommentsDto(comments));
            teamMemberService.insertNotifications("Gino", new TeamMemberNotificationsDto(notifications));

            isInitialized = true;
        }
        nRecords = 2;
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
    void getUserByUsernameTest() throws Exception
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
    void getUserIssuesByUsernameTest() throws Exception
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
    void getUserNotificationsByUsernameTest() throws Exception
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
    void getUserCommentsByUsernameTest() throws Exception
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
                .andExpect(jsonPath("$.comments[0].issue.idIssue").value(1))
                .andExpect(jsonPath("$.comments[0].teamMember.username").value("Gino"))
                .andDo(print());

        mvc.perform(get("/api/members/comments/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void signUpTest() throws Exception
    {
        mvc.perform(put("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void postCommentsTest() throws Exception
    {
        mvc.perform(put("/api/members/comments/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void sendNotificationTest() throws Exception
    {
        mvc.perform(put("/api/members/notifications/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void changePasswordTest() throws Exception
    {
        mvc.perform(post("/api/members/password/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void promoteMembersTest() throws Exception
    {
        mvc.perform(post("/api/members/promote/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void changeStatusNotificationsTest() throws Exception
    {
        mvc.perform(post("/api/members/notifications/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeMemberTest() throws Exception
    {
        mvc.perform(delete("/api/members/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void removeNotificationsTest() throws Exception
    {
        mvc.perform(delete("/api/members/notifications/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
