package com.okta.infra.jiraapiclient.exampletests;

import com.okta.infra.api.domain.changelog.ChangeLogData;
import com.okta.infra.api.domain.changelog.Item;
import com.okta.infra.domain.DataCL;
import com.okta.infra.jiraapiclient.services.JiraApiService;
import com.okta.infra.repositories.DataCLRepository;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.Rollback;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class JiraApiDataReadTest {
    @Autowired
    JiraApiService jiraApiService;

    @Value("classpath:data/input")
    private Resource inputFile;

    private List<String> issueIdList = new ArrayList<>();

    private int offset = 4000;
    private int listSize = 4250;



    @Mock
    DataCLRepository dataCLRepository;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);


        InputStream inputStream = inputFile.getInputStream();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            issueIdList = reader.lines().collect(Collectors.toList());
            listSize = issueIdList.size();
            System.out.println("List size : " + issueIdList.size());
        }
    }

    @Test
    public void testReadDataFile1stGroup() {

//        int localIndex = 1;
//        executeLoop(localIndex, false);

            ChangeLogData changeLogData;
            int i =0;
            String issueId = "OPS-120709";
            changeLogData = jiraApiService.getChangeLogData(issueId);
            processChangeLogData(changeLogData, issueId, i);
    }

    @Test
    public void testReadDataFile2ndGroup() {
        int localIndex = 2;
        executeLoop(localIndex, false);
    }


    @Test
    public void testReadDataFile3rdGroup() {
        int localIndex = 3;
        executeLoop(localIndex, false);
    }

    @Test
    public void testReadDataFile4thGroup() {
        int localIndex = 4;
        executeLoop(localIndex, false);
    }

    @Test
    public void testReadDataFile5thGroup() {
        int localIndex = 5;
        executeLoop(localIndex, false);
    }

    @Test
    public void testReadDataFile6thGroup() {
        int localIndex = 6;
        executeLoop(localIndex, false);
    }

    @Test
    public void testReadDataFile7thGroup() {
        int localIndex = 7;
        executeLoop(localIndex, false);
    }

    @Test
    public void testReadDataFile8thGroup() {
        int localIndex = 8;
        executeLoop(localIndex, false);
    }

    @Test
    public void testReadDataFile9thGroup() {
        int localIndex = 9;
        executeLoop(localIndex, false);
    }

    @Test
    public void testReadDataFile10thGroup() {
        int localIndex = 10;
        executeLoop(localIndex, true);
    }


    private void executeLoop(int localIndex, boolean last) {
        int multiplier = (listSize - offset) / 10;
        int end = multiplier * localIndex;
        end += offset;
        int start = end - multiplier;

        if (last) {
            end = listSize;
        }

//        System.out.println(start + " - " + end);
        ChangeLogData changeLogData;
        for (int i = start; i < end; i++) {
            String issueId = issueIdList.get(i);
            changeLogData = jiraApiService.getChangeLogData(issueId);
            processChangeLogData(changeLogData, issueId, i);
        }
    }

    private void processChangeLogData(ChangeLogData changeLogData, String issueId, int i) {

        if (changeLogData.getValues().size() == 0) {
//            System.out.println("Issue No. " + (i + 1) + "," + issueId + "," + "Size=0");
            System.out.println((i + 1) + "," + issueId);
            return;
        }

        LocalDateTime lastStatusDT = changeLogData.getValues().get(0).getCreatedDate();
        OutputData outputData = new OutputData();
        outputData.setIssueId(issueId);
        for (com.okta.infra.api.domain.changelog.Value value : changeLogData.getValues()) {
            for (Item item : value.getItems()) {
                if (item.getField().contains("status")) {
                    Duration duration = Duration.between(lastStatusDT, value.getCreatedDate());

                    lastStatusDT = value.getCreatedDate();
                    outputData.getStatusDuration().put(item.getToString(), duration.toMillis());
                    outputData.getStatusDurationString().put(item.getToString(),
                            DurationFormatUtils.formatDuration(duration.toMillis(),
                                    "dd:HH:mm:ss:SSS", false)
                    );
                }
            }
        }
        printOutputData(outputData, i);
    }

    private void printOutputData(OutputData outputData, int i) {
        DataCL data = new DataCL();

        data.setIndex(i + 1);
        data.setIssueId(outputData.getIssueId());
        outputData.getStatusDuration().forEach((key, value) -> {
//            System.out.println(key + " , " + value);
            switch (key.toLowerCase()) {
                case "new":
                    data.setDurationNew(value);
                    break;
                case "ops team lead review":
                    data.setDurationOpsTeamLeadReview(value);
                    break;
                case "qa review":
                    data.setDurationQAReview(value);
                    break;
                case "cloudboss requested":
                    data.setDurationCloudbossRequested(value);
                    break;
                case "ops team lead approved":
                    data.setDurationOpsTeamLeadApproved(value);
                    break;
                case "vp approved":
                    data.setDurationVPApproved(value);
                    break;
                case "in progress":
                    data.setDurationInProgress(value);
                    break;
                case "resolved":
                    data.setDurationResolved(value);
                    break;
                case "vp approval needed":
                    data.setDurationVPApproved(value);
                    break;
                case "cloudboss approved":
                    data.setDurationCloudbossApproved(value);
                    break;
            }
        });


//        System.out.println(data.toString());
//        dataCLRepository.save(data);

        System.out.println(data.getIndex() + ","
                + data.getIssueId() + ","
                + "New" + "," + data.getDurationNew() + ","
                + DurationFormatUtils.formatDuration(data.getDurationNew(), "HH:mm:ss.SSS", true) + ","
                + "Ops Team Lead Review" + "," + data.getDurationOpsTeamLeadReview() + ","
                + DurationFormatUtils.formatDuration(data.getDurationOpsTeamLeadReview(), "HH:mm:ss.SSS", true) + ","
                + "QA Review" + "," + data.getDurationQAReview() + ","
                + DurationFormatUtils.formatDuration(data.getDurationQAReview(), "HH:mm:ss.SSS", true) + ","
                + "Cloudboss Requested" + "," + data.getDurationCloudbossRequested() + ","
                + DurationFormatUtils.formatDuration(data.getDurationCloudbossRequested(), "HH:mm:ss.SSS", true) + ","
                + "Ops Team Lead Approved" + "," + data.getDurationOpsTeamLeadApproved() + ","
                + DurationFormatUtils.formatDuration(data.getDurationOpsTeamLeadApproved(), "HH:mm:ss.SSS", true) + ","
                + "VP Approved" + "," + data.getDurationVPApproved() + ","
                + DurationFormatUtils.formatDuration(data.getDurationVPApproved(), "HH:mm:ss.SSS", true) + ","
                + "In Progress" + "," + data.getDurationInProgress() + ","
                + DurationFormatUtils.formatDuration(data.getDurationInProgress(), "HH:mm:ss.SSS", true) + ","
                + "Resolved" + "," + data.getDurationResolved() + ","
                + DurationFormatUtils.formatDuration(data.getDurationResolved(), "HH:mm:ss.SSS", true) + ","
                + "VP Approval Needed" + "," + data.getDurationVPApproved() + ","
                + DurationFormatUtils.formatDuration(data.getDurationVPApproved(), "HH:mm:ss.SSS", true) + ","
                + "Cloudboss Approved" + "," + data.getDurationCloudbossApproved() + ","
                + DurationFormatUtils.formatDuration(data.getDurationCloudbossApproved(), "HH:mm:ss.SSS", true) + ","
        );

//        System.out.println("Issue No. " + (i + 1) + "," + outputData.getIssueId() + ","
//                        + "Size=" + outputData.getStatusDuration().size() + ","
//                + outputData.getStatusDuration()
//                        + outputData.getStatusDurationString()
//              +  DurationFormatUtils.formatDuration(12324324, "HH:mm:ss", false)
//        );
    }
}