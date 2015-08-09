package com.daeliin.framework.sample.api.userpermission.model;

import com.daeliin.framework.sample.api.user.model.User;
import com.daeliin.framework.sample.api.permission.model.Permission;
import com.daeliin.framework.commons.security.details.PersistentUserPermissionDetails;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_permission")
public class UserPermission extends PersistentUserPermissionDetails<User, Permission> {
    
    private static final long serialVersionUID = -4035977030615510296L;

    public UserPermission() {
    }
}
