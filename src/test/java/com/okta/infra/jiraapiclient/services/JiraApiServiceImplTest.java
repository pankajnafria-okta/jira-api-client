package com.okta.infra.jiraapiclient.services;

import com.okta.infra.api.domain.changelog.ChangeLogData;
import com.okta.infra.api.domain.changelog.Item;
import com.okta.infra.api.domain.changelog.Value;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

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

        LocalDateTime lastStatusDT = changeLogData.getValues().get(0).getCreatedDate();
        System.out.println("\t\tField,\t\tFrom String,\t\tTo String");
        for (Value value : changeLogData.getValues()) {
            for (Item item : value.getItems()) {
                if (item.getField().contains("status")) {
                    System.out.println("\t\t <" + item.getField() + ">,\t\t <"
//                                                + item.getFromString() + ">,<"
                                                + item.getToString() + ">,\t\t <"
                                                + Duration.between(lastStatusDT, value.getCreatedDate()).toMillis()
                                                +">");
                    lastStatusDT = value.getCreatedDate();
                }
            }
        }

//        changeLogData.getValues().forEach(value -> {
////            System.out.println(value.getCreated() + "-> Items Count : " + value.getItems().size());
//            value.getItems().forEach(item -> {
//                if (item.getField().contains("status")) {
//                    System.out.println("\t\t <" + item.getField() + ">,<" + item.getFromString() + ">,<" + item.getToString() + ">,");
//                    lastStatusDT = value.getCreatedDate();
//                }
//
//            });
//        });
        assertNotNull(changeLogData);
    }

    @Test
    public void getChangeLogDataWithTimeDifference() {
        ChangeLogData changeLogData = jiraApiService.getChangeLogData(ISSUE_ID);

        LocalDateTime lastStatusDT = changeLogData.getValues().get(0).getCreatedDate();
        System.out.println("\t\tField,\t\tFrom String,\t\tTo String");
        for (Value value : changeLogData.getValues()) {
            for (Item item : value.getItems()) {
                if (item.getField().contains("status")) {
                    Duration duration = Duration.between(lastStatusDT, value.getCreatedDate());
                    System.out.println("\t\t" + item.getField() + ","
                                    + item.getToString() + ","
                                    + DurationFormatUtils.formatDuration(duration.toMillis(), "dd:HH:MM:ss", true)
                                        );
                    lastStatusDT = value.getCreatedDate();
                }
            }
        }
    }
}