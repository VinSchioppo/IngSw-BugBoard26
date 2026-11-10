package com.ingsw.bugboard26;

import com.ingsw.bugboard26.service.issue.IssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IssueControllerTest
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    private IssueService issueService;

    @BeforeEach
    void setUp()
    {

    }

    @Test
    void getIssueByIdIssueTest() throws Exception
    {

    }

    @Test
    void getIssueByMemberUsernameTest() throws Exception
    {

    }

    @Test
    void getIssueByTagTest() throws Exception
    {

    }

    @Test
    void getIssueByPriorityTest() throws Exception
    {

    }

    @Test
    void getIssueCommentsTest() throws Exception
    {

    }

    @Test
    void getIssueLogsTest() throws Exception
    {

    }

    @Test
    void assignMemberTest() throws Exception
    {

    }

    @Test
    void logChangeTest() throws Exception
    {

    }

    @Test
    void changeStatusTest() throws Exception
    {

    }

    @Test
    void removeIssueTest() throws Exception
    {

    }
}
