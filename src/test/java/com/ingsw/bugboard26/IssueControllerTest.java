package com.ingsw.bugboard26;

import com.ingsw.bugboard26.dto.issue.IssueInfoDto;
import com.ingsw.bugboard26.dto.issue.IssueLogsDto;
import com.ingsw.bugboard26.dto.issue.IssueMemberDto;
import com.ingsw.bugboard26.dto.issuecomment.IssueCommentMemberDto;
import com.ingsw.bugboard26.dto.issuelog.IssueLogDto;
import com.ingsw.bugboard26.dto.issuetag.IssueTagDto;
import com.ingsw.bugboard26.dto.project.ProjectDto;
import com.ingsw.bugboard26.dto.project.ProjectIssuesDto;
import com.ingsw.bugboard26.dto.teammember.TeamMemberCommentsDto;
import com.ingsw.bugboard26.dto.teammember.TeamMemberInfoDto;
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
class IssueControllerTest
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private TeamMemberService teamMemberService;

    @BeforeEach
    void setUp()
    {
        projectService.insertProject(new ProjectDto("Test Project", "Un progetto per eseguire test."));

        List<IssueTagDto> tags = new ArrayList<>();
        tags.add(new IssueTagDto("test"));

        List<IssueInfoDto> issues = new ArrayList<>();
        issues.add(new IssueInfoDto(0L, null, "Test Issue 1", "Issue 1 per eseguire un test.",
                1, false, null, tags, null));
        issues.add(new IssueInfoDto(0L, null, "Test Issue 2", "Issue 2 per eseguire un test.",
                2, false, null, null, null));

        projectService.insertIssues(1L, new ProjectIssuesDto("Test Project", issues));

        List<TeamMemberInfoDto> teamMembers = new ArrayList<>();
        teamMembers.add(new TeamMemberInfoDto("Mario", "mario@gmail.com", "Mario123", true));

        teamMemberService.insertTeamMembers(teamMembers);

        List<IssueCommentMemberDto> comments = new ArrayList<>();
        comments.add(new IssueCommentMemberDto(2L, "Test per issue comment.", null));

        TeamMemberCommentsDto memberComments = new TeamMemberCommentsDto(comments);

        teamMemberService.insertComments("Mario", memberComments);

        List<IssueLogDto> logs = new ArrayList<>();
        logs.add(new IssueLogDto("Test per gli issue logs.", null));

        IssueMemberDto issueTeamMember = new IssueMemberDto(teamMembers.get(0));
        IssueLogsDto issueLogs = new IssueLogsDto(logs);

        issueService.insertLogs(1L, issueLogs);
        issueService.insertMember(1L, issueTeamMember);
    }

    @Test
    void getIssueByIdIssueTest() throws Exception
    {
        mvc.perform(get("/api/issues/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(11)))
                .andExpect(jsonPath("$.title").value("Test Issue 1"))
                .andExpect(jsonPath("$.description").value("Issue 1 per eseguire un test."))
                .andExpect(jsonPath("$.priority").value(1))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.project.title").value("Test Project"))
                .andExpect(jsonPath("$.teamMember.username").value("Mario"))
                .andExpect(jsonPath("$.tags[0].nameTag").value("test"))
                .andExpect(jsonPath("$.issueLogs[0].description").value("Test per gli issue logs."))
                .andDo(print());

        mvc.perform(get("/api/issues/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getIssueCommentsTest() throws Exception
    {
        mvc.perform(get("/api/issues/comments/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.comments[0].description").value("Test per issue comment."))
                .andDo(print());

        mvc.perform(get("/api/issues/comments/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getIssueLogsTest() throws Exception
    {
        mvc.perform(get("/api/issues/logs/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.issueLogs[0].description").value("Test per gli issue logs."))
                .andDo(print());

        mvc.perform(get("/api/issues/logs/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getIssueByMemberUsernameTest() throws Exception
    {
        mvc.perform(get("/api/issues?member=Mario")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].idIssue").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Issue 1"))
                .andExpect(jsonPath("$[0].description").value("Issue 1 per eseguire un test."))
                .andExpect(jsonPath("$[0].priority").value(1))
                .andExpect(jsonPath("$[0].status").value(false))
                .andExpect(jsonPath("$[0].memberUsername").value("Mario"))
                .andExpect(jsonPath("$[0].tags[0].nameTag").value("test"))
                .andDo(print());

        mvc.perform(get("/api/issues?member=Gino")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andDo(print());
    }

    @Test
    void getIssueByTagTest() throws Exception
    {
        mvc.perform(get("/api/issues?tag=test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].idIssue").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Issue 1"))
                .andExpect(jsonPath("$[0].description").value("Issue 1 per eseguire un test."))
                .andExpect(jsonPath("$[0].priority").value(1))
                .andExpect(jsonPath("$[0].status").value(false))
                .andExpect(jsonPath("$[0].memberUsername").value("Mario"))
                .andExpect(jsonPath("$[0].tags[0].nameTag").value("test"))
                .andDo(print());

        mvc.perform(get("/api/issues?tag=final")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andDo(print());
    }

    @Test
    void getIssueByPriorityTest() throws Exception
    {
        mvc.perform(get("/api/issues?priority=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].idIssue").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Issue 1"))
                .andExpect(jsonPath("$[0].description").value("Issue 1 per eseguire un test."))
                .andExpect(jsonPath("$[0].priority").value(1))
                .andExpect(jsonPath("$[0].status").value(false))
                .andExpect(jsonPath("$[0].memberUsername").value("Mario"))
                .andExpect(jsonPath("$[0].tags[0].nameTag").value("test"))
                .andDo(print());

        mvc.perform(get("/api/issues?priority=0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andDo(print());
    }

    @Test
    void logChangeTest() throws Exception
    {
        JsonObject log = new JsonObject();
        log.addProperty("description", "Test insert issue logs.");

        JsonArray logs = new JsonArray();
        logs.add(log);

        JsonObject issueLogs = new JsonObject();
        issueLogs.add("issueLogs", logs);

        mvc.perform(put("/api/issues/logs/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issueLogs.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        mvc.perform(get("/api/issues/logs/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.issueLogs[0].description").value("Test insert issue logs."))
                .andDo(print());

        mvc.perform(put("/api/issues/logs/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issueLogs.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        log = new JsonObject();
        log.addProperty("description", "Nuovo test insert issue logs.");

        logs = new JsonArray();
        logs.add(log);
        logs.add(log);

        issueLogs = new JsonObject();
        issueLogs.add("issueLogs", logs);

        mvc.perform(put("/api/issues/logs/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issueLogs.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        mvc.perform(post("/api/issues/logs/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issueLogs.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        log = new JsonObject();

        logs = new JsonArray();
        logs.add(log);

        issueLogs = new JsonObject();
        issueLogs.add("issueLogs", logs);

        mvc.perform(put("/api/issues/logs/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issueLogs.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        issueLogs = new JsonObject();

        mvc.perform(put("/api/issues/logs/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issueLogs.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void assignMemberTest() throws Exception
    {
        JsonObject teamMember = new JsonObject();
        teamMember.addProperty("username", "Mario");

        JsonObject issue = new JsonObject();
        issue.add("teamMember", teamMember);

        mvc.perform(get("/api/issues/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(11)))
                .andExpect(jsonPath("$.title").value("Test Issue 2"))
                .andExpect(jsonPath("$.description").value("Issue 2 per eseguire un test."))
                .andExpect(jsonPath("$.priority").value(2))
                .andExpect(jsonPath("$.status").value(false))
                .andDo(print());

        mvc.perform(post("/api/issues/member/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issue.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mvc.perform(get("/api/issues/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(11)))
                .andExpect(jsonPath("$.title").value("Test Issue 2"))
                .andExpect(jsonPath("$.description").value("Issue 2 per eseguire un test."))
                .andExpect(jsonPath("$.priority").value(2))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.teamMember.username").value("Mario"))
                .andDo(print());

        mvc.perform(post("/api/issues/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issue.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        teamMember = new JsonObject();
        teamMember.addProperty("username", "Gino");

        issue = new JsonObject();
        issue.add("teamMember", teamMember);

        mvc.perform(post("/api/issues/member/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issue.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        teamMember = new JsonObject();
        teamMember.addProperty("username", "");

        issue = new JsonObject();
        issue.add("teamMember", teamMember);

        mvc.perform(post("/api/issues/member/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issue.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        issue = new JsonObject();

        mvc.perform(post("/api/issues/member/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issue.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void changeStatusTest() throws Exception
    {
        JsonObject issue = new JsonObject();
        issue.addProperty("status", true);

        mvc.perform(get("/api/issues/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(11)))
                .andExpect(jsonPath("$.title").value("Test Issue 2"))
                .andExpect(jsonPath("$.description").value("Issue 2 per eseguire un test."))
                .andExpect(jsonPath("$.priority").value(2))
                .andExpect(jsonPath("$.status").value(false))
                .andDo(print());

        mvc.perform(post("/api/issues/status/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issue.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mvc.perform(get("/api/issues/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(11)))
                .andExpect(jsonPath("$.title").value("Test Issue 2"))
                .andExpect(jsonPath("$.description").value("Issue 2 per eseguire un test."))
                .andExpect(jsonPath("$.priority").value(2))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.project.title").value("Test Project"))
                .andDo(print());

        mvc.perform(post("/api/issues/status/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issue.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        issue = new JsonObject();

        mvc.perform(post("/api/issues/status/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(issue.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void removeIssueTest() throws Exception
    {
        mvc.perform(get("/api/issues/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(11)))
                .andExpect(jsonPath("$.title").value("Test Issue 2"))
                .andExpect(jsonPath("$.description").value("Issue 2 per eseguire un test."))
                .andExpect(jsonPath("$.priority").value(2))
                .andExpect(jsonPath("$.status").value(false))
                .andDo(print());

        mvc.perform(delete("/api/issues/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(delete("/api/issues/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        mvc.perform(delete("/api/issues/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
