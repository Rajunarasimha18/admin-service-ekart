package com.raju.admin.enums;


public enum CommonStatus {
	 SUCCESS("SUCCESS"),
	   ACTIVE("ACTIVE"),
	   FAILURE("FAILURE"),
	   LOGGEDIN("LOGGED IN"),
	   LOGGEDOUT("LOGGED OUT"),
	   ORGANIZATION_ALREADY_EXIST("ORGANIZATION ALREADY EXIST"),
	   SUCCESFULLY_SAVED("SUCCESFULLY SAVED"),
	   SUCCESFULLY_UPDATED("SUCCESFULLY UPDATED"),
	   SUCCESFULLY_DELETED("SUCCESFULLY DELETED"),
	   SUCCESFULLY_REGISTERED("SUCCESFULLY REGISTERED");

	   private String statusValue;

	   private CommonStatus(String statusValue) {
	      this.statusValue = statusValue;
	   }

	   public String getStatusValue() {
	      return this.statusValue;
	   }
}
