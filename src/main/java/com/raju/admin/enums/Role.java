package com.raju.admin.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
   ROLE_SUPER_ADMIN,
   ROLE_ADMIN,
   ROLE_USER,
   ROLE_DRIVER,
   ROLE_RIDER;

   public String getAuthority() {
      return this.name();
   }
}