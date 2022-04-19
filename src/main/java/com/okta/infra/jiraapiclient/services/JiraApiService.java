package com.okta.infra.jiraapiclient.services;

import com.okta.infra.api.domain.changelog.ChangeLogData;

public interface JiraApiService {
    ChangeLogData getChangeLogData(String issueId);
}
