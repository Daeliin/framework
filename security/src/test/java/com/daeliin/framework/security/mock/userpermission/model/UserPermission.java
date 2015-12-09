package com.daeliin.framework.security.mock.userpermission.model;

import com.daeliin.framework.commons.security.details.PersistentUserPermissionDetails;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.daeliin.framework.security.mock.permission.model.Permission;
import com.daeliin.framework.security.mock.user.model.User;

@Entity
@Table(name = "user_permission")
public class UserPermission extends PersistentUserPermissionDetails<User, Permission> {
    
    private static final long serialVersionUID = -4035977030615510296L;

    public UserPermission() {
    }
}
