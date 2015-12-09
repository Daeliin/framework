package com.daeliin.framework.security.mock.user.model;

import com.daeliin.framework.commons.security.details.PersistentUserDetails;
import javax.persistence.Entity;

@Entity
public class User extends PersistentUserDetails {

    private static final long serialVersionUID = 574889976384058563L;
    
    public User() {
    }
}
