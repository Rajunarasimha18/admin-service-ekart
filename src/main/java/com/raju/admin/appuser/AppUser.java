package com.raju.admin.appuser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.raju.admin.common.SequenceListener;
import com.raju.admin.enums.Role;

import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "app_user")
@EntityListeners(SequenceListener.class)
@ToString
public class AppUser implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String SEQUENCE_NAME = "common_sequence";

	@Id
	private Long userId;

	@Field("full_name")
	private String fullName;
	
	@Field("user_name")
	private String userName;
	
	@Field(name = "password")
	private String password;
	
	@Field(name = "mobile")
	private String mobile;
	
	@Field(name = "user_email")
	private String email;
	
	@Field(name = "current_token")
	private String currentToken;
	
	@Field(name = "wallet_balance")
	private Double walletBalance;
	
	@Field("roles")
	private List<Role> roles;
	
	@Field("is_active")
	private Boolean isActive = true;

	@Field("created_by")
	private String createdBy;

	@Field("created_date")
	private Date createdDate;

	@Field("created_system_ip")
	private String createdSystemIp;

	@Field("is_deleted_value")
	private Boolean isDeletedValue = false;

	@Field("modified_by")
	private String modifiedBy;

	@Field("modified_date")
	private Date modifiedDate;

	@Field("modified_system_ip")
	private String modifiedSystemIp;

}
