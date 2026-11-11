package com.ingsw.bugboard26;

import com.ingsw.bugboard26.dto.issue.IssueInfoDto;
import com.ingsw.bugboard26.dto.project.ProjectDto;
import com.ingsw.bugboard26.dto.project.ProjectIssuesDto;
import com.ingsw.bugboard26.service.project.ProjectService;
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
class ProjectControllerTest
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProjectService projectService;

    private int nRecords;

    @BeforeEach
    void setUp()
    {
        projectService.insertProject(new ProjectDto("Test Project 1", "Un progetto per eseguire test."));
        projectService.insertProject(new ProjectDto("Test Project 2", "Un progetto per eseguire test."));

        List<IssueInfoDto> issues = new ArrayList<>();
        issues.add(new IssueInfoDto(0L, null, "Issue Test", "Issue per eseguire un test.",
                1, false, null, null, null));

        projectService.insertIssues(1L, new ProjectIssuesDto("Test Project 1", issues));

        nRecords = 2;
    }

    @Test
    void getAllProjectsTest() throws Exception
    {
        mvc.perform(get("/api/projects")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(nRecords)))
                .andDo(print());
    }

    @Test
    void getProjectTest() throws Exception
    {
        mvc.perform(get("/api/projects/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.title").value("Test Project 1"))
                .andExpect(jsonPath("$.issues[0].idIssue").value(1))
                .andExpect(jsonPath("$.issues[0].title").value("Issue Test"))
                .andExpect(jsonPath("$.issues[0].description").value("Issue per eseguire un test."))
                .andExpect(jsonPath("$.issues[0].priority").value(1))
                .andExpect(jsonPath("$.issues[0].status").value(false))
                .andDo(print());

        mvc.perform(get("/api/projects/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createProjectTest() throws Exception
    {
        JsonObject project = new JsonObject();
        project.addProperty("title", "Project 3");
        project.addProperty("description", "Il nuovo progetto per eseguire il test.");

        mvc.perform(put("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        mvc.perform(get("/api/projects/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.title").value("Project 3"))
                .andDo(print());

        project = new JsonObject();

        mvc.perform(put("/api/projects/issues/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void reportIssueTest() throws Exception
    {
        JsonObject issue = new JsonObject();
        issue.addProperty("title", "Issue Project 2");
        issue.addProperty("description", "La nuova issue del progetto 2.");
        issue.addProperty("priority", 1);

        JsonArray issues = new JsonArray();
        issues.add(issue);

        JsonObject project = new JsonObject();
        project.addProperty("title", "Project 3");
        project.add("issues", issues);

        mvc.perform(put("/api/projects/issues/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        mvc.perform(get("/api/projects/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.issues", hasSize(1)))
                .andExpect(jsonPath("$.issues[0].idIssue").value(2))
                .andExpect(jsonPath("$.issues[0].title").value("Issue Project 2"))
                .andExpect(jsonPath("$.issues[0].description").value("La nuova issue del progetto 2."))
                .andExpect(jsonPath("$.issues[0].priority").value(1))
                .andExpect(jsonPath("$.issues[0].status").value(false))
                .andDo(print());

        mvc.perform(put("/api/projects/issues/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        issue = new JsonObject();
        issue.addProperty("title", "Issue 3 Project 2");

        issues = new JsonArray();
        issues.add(issue);

        project = new JsonObject();
        project.add("issues", issues);

        mvc.perform(put("/api/projects/issues/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        issue = new JsonObject();
        issue.addProperty("description", "La ancora più nuova issue del progetto 2.");

        issues = new JsonArray();
        issues.add(issue);

        project = new JsonObject();
        project.add("issues", issues);

        mvc.perform(put("/api/projects/issues/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        project = new JsonObject();

        mvc.perform(put("/api/projects/issues/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void changeInfoTest() throws Exception
    {
        mvc.perform(get("/api/projects/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.title").value("Test Project 2"))
                .andDo(print());

        JsonObject project = new JsonObject();
        project.addProperty("title", "Test Update Project 2");
        project.addProperty("description", "Un progetto per eseguire test.");

        mvc.perform(post("/api/projects/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mvc.perform(get("/api/projects/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.title").value("Test Update Project 2"))
                .andDo(print());

        project = new JsonObject();
        project.addProperty("title", "Test Update Project 2");

        mvc.perform(post("/api/projects/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        project = new JsonObject();
        project.addProperty("description", "Un progetto per eseguire test.");

        mvc.perform(post("/api/projects/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());

        mvc.perform(post("/api/projects/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        project = new JsonObject();

        mvc.perform(post("/api/projects/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(project.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void removeProjectTest() throws Exception
    {
        mvc.perform(get("/api/projects/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andDo(print());

        mvc.perform(delete("/api/projects/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        mvc.perform(delete("/api/projects/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        mvc.perform(delete("/api/projects/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
