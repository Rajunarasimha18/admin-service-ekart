package com.raju.admin.appuser;

import java.util.Date;
import java.util.List;

import com.raju.admin.enums.Role;

import lombok.Data;

@Data
public class UserDTO {

	private Long userId;

	private String fullName;

	private String userName;

	private String password;

	private String mobile;

	private String email;

	private String currentToken;

	private Double walletBalance;

	private List<Role> roles;

	private Boolean isActive;

	private String createdBy;

	private Date createdDate;

	private Boolean isDeletedValue = false;

	private String modifiedBy;

	private Date modifiedDate;

	private List<String> permissions;

	private String rolesAssigned;

	private String status;

	private String message;

}
