package com.okta.infra.jiraapiclient.exampletests;

import java.util.HashMap;
import java.util.Map;

public class OutputData {
    private String issueId;
    private Map<String, Long> statusDuration = new HashMap<>();
    private Map<String, String> statusDurationString = new HashMap<>();

    public OutputData() {
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public Map<String, Long> getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(Map<String, Long> statusDuration) {
        this.statusDuration = statusDuration;
    }

    public Map<String, String> getStatusDurationString() {
        return statusDurationString;
    }

    public void setStatusDurationString(Map<String, String> statusDurationString) {
        this.statusDurationString = statusDurationString;
    }
}
