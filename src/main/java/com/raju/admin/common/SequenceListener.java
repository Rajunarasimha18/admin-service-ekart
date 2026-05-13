package com.raju.admin.common;

import java.lang.reflect.Field;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.raju.admin.appuser.AppUser;
import com.raju.admin.roles.Roles;


@Component
public class SequenceListener extends AbstractMongoEventListener<Object> {

	@Autowired
	private SequenceGeneratorService sequenceGenerator;
	
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Object> event) {
		Object entity = event.getSource();
		if (entity == null)
			return;

		try {
			// 🔹 Assign numeric IDs for each master entity
			if (entity instanceof AppUser au) {
				if (au.getUserId() == null) {
					au.setUserId(sequenceGenerator.generateSequence("common_sequence"));
				}
			} 
			else if (entity instanceof Roles rl) {
				if (rl.getRoleId() == null) {
					rl.setRoleId(sequenceGenerator.generateSequence("roles_sequence"));
				}
			}

			if (!isNewEntity(entity)) {
				prePersist(entity);
			} else {
				preUpdate(entity);
			}

//			 🔹 Apply metadata logic
//            prePersist(entity);
//            preUpdate(entity);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public synchronized void prePersist(Object entity) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = entity.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			String name = field.getName();

			switch (name) {
			case "createdBy" -> {
				field.set(entity, "admin@dss.aboosto.com");				
			}
			case "createdSystemIp" -> {
				field.set(entity, "127.0.0.1");
			}
			case "createdDate" -> {
			    if (field.get(entity) == null) {
			        field.set(entity, new Date());
			    }
			}
			case "isDeletedValue" -> {
			    if (field.get(entity) == null) {
			        field.set(entity, false);
			    }
			}
			default -> {
				// No action
			}
			}
		}
	}

	/**
	 * Sets modification-related fields before updating an existing record.
	 */
	public synchronized void preUpdate(Object entity) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = entity.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			String name = field.getName();

			switch (name) {
			case "modifiedBy" -> {
				field.set(entity, "admin@dss.aboosto.com");
			}
			case "modifiedSystemIp" -> {
				field.set(entity, "127.0.0.1");
			}
			case "modifiedDate" -> {
				field.set(entity, new Date());				
			}
			default -> {
				// No action
			}
			}
		}
	}

	private boolean isNewEntity(Object entity) {

		try {

			for (Field field : entity.getClass().getDeclaredFields()) {

				if (field.isAnnotationPresent(org.springframework.data.annotation.Id.class)) {

					field.setAccessible(true);

					return field.get(entity) == null;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
