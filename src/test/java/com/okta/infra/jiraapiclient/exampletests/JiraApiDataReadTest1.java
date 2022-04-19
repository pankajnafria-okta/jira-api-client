package com.okta.infra.jiraapiclient.exampletests;

import com.okta.infra.api.domain.changelog.ChangeLogData;
import com.okta.infra.api.domain.changelog.Item;
import com.okta.infra.jiraapiclient.services.JiraApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class JiraApiDataReadTest1 {
    @Autowired
    JiraApiService jiraApiService;

    @Value("classpath:data/input")
    private Resource inputFile;

    private List<String> issueIdList = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        InputStream inputStream = inputFile.getInputStream();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            issueIdList = reader.lines().collect(Collectors.toList());
            System.out.println(issueIdList.size());
        }
    }

//    @Test
//    public void testReadDataFile500() {
//        ChangeLogData changeLogData;
//        List<OutputData> outputDataList = new ArrayList<>();
//
//        int size = 500;//issueIdList.size();
//        for (int i = 0; i <size; i++) {
//            String issueId = issueIdList.get(i);
//            changeLogData = jiraApiService.getChangeLogData(issueId);
//            LocalDateTime lastStatusDT = changeLogData.getValues().get(0).getCreatedDate();
//            OutputData outputData = new OutputData();
//            outputData.setIssueId(issueId);
//            for (com.okta.infra.api.domain.changelog.Value value : changeLogData.getValues()) {
//                for (Item item : value.getItems()) {
//                    if (item.getField().contains("status")) {
//                        Duration duration = Duration.between(lastStatusDT, value.getCreatedDate());
//
//                        lastStatusDT = value.getCreatedDate();
//                        outputData.getStatusDuration().put(item.getToString(), duration.toMillis());
//                    }
//                }
//            }
//            System.out.println("Issue No. " + (i + 1) + "," + outputData.getIssueId() + "," + outputData.getStatusDuration());
//            outputDataList.add(outputData);
//        }
//
//        System.out.println(outputDataList.size());
//    }

    @Test
    public void testReadDataFile1000() {
        ChangeLogData changeLogData;
        List<OutputData> outputDataList = new ArrayList<>();

        int size = 1000;//issueIdList.size();
        for (int i = 500; i < size; i++) {
            String issueId = issueIdList.get(i);
            changeLogData = jiraApiService.getChangeLogData(issueId);
            LocalDateTime lastStatusDT = changeLogData.getValues().get(0).getCreatedDate();
            OutputData outputData = new OutputData();
            outputData.setIssueId(issueId);
            for (com.okta.infra.api.domain.changelog.Value value : changeLogData.getValues()) {
                for (Item item : value.getItems()) {
                    if (item.getField().contains("status")) {
                        Duration duration = Duration.between(lastStatusDT, value.getCreatedDate());

                        lastStatusDT = value.getCreatedDate();
                        outputData.getStatusDuration().put(item.getToString(), duration.toMillis());
                    }
                }
            }
            System.out.println("Issue No. " + (i + 1) + "," + outputData.getIssueId() + "," + outputData.getStatusDuration());
            outputDataList.add(outputData);
        }

        System.out.println(outputDataList.size());
    }
}