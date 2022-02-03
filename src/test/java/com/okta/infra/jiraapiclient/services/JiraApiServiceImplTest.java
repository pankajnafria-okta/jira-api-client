package com.okta.infra.jiraapiclient.services;

import com.okta.infra.api.domain.changelog.ChangeLogData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JiraApiServiceImplTest {
    private final String ISSUE_ID = "OPS-161326";

    @Autowired
    JiraApiService jiraApiService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getChangeLogData() {
        ChangeLogData changeLogData = jiraApiService.getChangeLogData(ISSUE_ID);

        assertNotNull(changeLogData);
    }
}