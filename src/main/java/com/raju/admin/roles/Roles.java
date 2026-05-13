package com.raju.admin.roles;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.raju.admin.common.SequenceListener;

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
@Document(collection = "roles")
@EntityListeners(SequenceListener.class)
@ToString
public class Roles implements Serializable {
	
	 private static final long serialVersionUID = 1L;

	 public static final String SEQUENCE_NAME = "roles_sequence";
	 
    @Id
    private Long roleId;

    @Field("role_name")
    private String roleName; 
    
    @Field("role_description")
    private String roleDescription;

    @Field("role_permisssions")
    private String rolePermissions; 
    
    @Field("permissions")
    private List<String> permissions;
	
    @Field("restricted")
	private List<String> restricted;
    
    @Field("remarks")
    private String remarks;

    @Field("status")
    private String status;

    @Field("created_by")
    private String createdBy;

    @Field("created_date")
    private Date createdDate;

    @Field("created_system_ip")
    private String createdSystemIp;

    @Field("is_deleted_value")
    private Boolean isDeletedValue=false;

    @Field("modified_by")
    private String modifiedBy;

    @Field("modified_date")
    private Date modifiedDate;

    @Field("modified_system_ip")
    private String modifiedSystemIp;
    
}
