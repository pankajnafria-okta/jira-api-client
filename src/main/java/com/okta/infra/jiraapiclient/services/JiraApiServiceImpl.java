package com.okta.infra.jiraapiclient.services;

import com.okta.infra.api.domain.changelog.ChangeLogData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class JiraApiServiceImpl implements JiraApiService {
    private RestTemplate restTemplate;
    private final String api_url;

    public JiraApiServiceImpl(RestTemplate restTemplate,@Value("${api.url}") String api_url) {
        this.restTemplate = restTemplate;
        this.api_url = api_url;
    }

    @Override
    public ChangeLogData getChangeLogData(String issueId) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(api_url + "/issue/" + issueId + "/changelog");
        return restTemplate.getForObject(uriComponentsBuilder.toUriString(), ChangeLogData.class);
    }
}
