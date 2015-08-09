package com.daeliin.framework.sample.api.permission.model;

import com.daeliin.framework.commons.security.details.PersistentPermissionDetails;
import javax.persistence.Entity;

@Entity
public class Permission extends PersistentPermissionDetails {
    
    private static final long serialVersionUID = -8365652154930643212L;

    public Permission() {
    }
}
