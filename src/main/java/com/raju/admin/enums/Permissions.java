package com.raju.admin.enums;

public enum Permissions {
	Dashboard("Dashboard"),
	Users("UserManagement/Users"),
	Roles("UserManagement/Roles"),
	Permissions("UserManagement/Permissions"),
	Country("ConfigManagement/Country"),
	State("ConfigManagement/State"),
	City("ConfigManagement/City"),
	Drivers("Drivers"),
	Riders("Riders"),
	VehicleCategory("Pricing/VehicleCategory"),
	BasePricing("Pricing/BasePricing"),
	SurgePricing("Pricing/SurgePricing"),
	ReferralCampaign("ReferralCampaign"),
	Promotions("Promotions"),
	Disputes("Disputes"),
	Analytics("Analytics"),
	Reports("Reports"),
	;

	private String statusValue;

	private Permissions(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getStatusValue() {
		return statusValue;
	}
}
