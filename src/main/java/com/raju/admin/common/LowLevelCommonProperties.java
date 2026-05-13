package com.raju.admin.common;

import java.util.Date;

import lombok.Data;

@Data
public class LowLevelCommonProperties {

	private String createdBy;

	private Date createdDate;

	private String createdSystemIp;

	private String modifiedBy;

	private Date dateModified;

	private String modifiedSystemIp;

	private boolean isDeleted = false;

	private String status;

	private String remarks;

}
