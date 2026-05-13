package com.raju.admin.common;

import java.util.Date;

import com.raju.admin.enums.CommonStatus;

import lombok.Data;

@Data
public class MediumLevelCommonProperties {
    private String createdBy;
    private Date createdDate;
    private String createdSystemIp;

    private String modifiedBy;
    private Date modifiedDate;
    private String modifiedSystemIp;

    private boolean isDeleted = false;
    private boolean isExpired = false;
    private boolean isBlocked = false;
    private boolean isActivated = true;

    private CommonStatus commonStatus;
}
