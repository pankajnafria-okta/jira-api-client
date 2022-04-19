package com.okta.infra.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@lombok.Data
@Entity
public class DataCL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int index = 0;
    private String issueId;
    private Long durationNew = 0L;
    private Long durationOpsTeamLeadReview = 0L;
    private Long durationQAReview = 0L;
    private Long durationCloudbossRequested = 0L;
    private Long durationOpsTeamLeadApproved = 0L;
    private Long durationVPApproved = 0L;
    private Long durationInProgress = 0L;
    private Long durationResolved = 0L;
    private Long durationVPApprovalNeeded = 0L;
    private Long durationCloudbossApproved = 0L;
}
