package com.daeliin.components.security.credentials.accountpermission;

import com.daeliin.components.domain.resource.UUIDPersistentResource;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.permission.Permission;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"account", "permission"}, callSuper = true)
@Table(name = "account_permission")
@Entity
public class AccountPermission extends UUIDPersistentResource {
    
    private static final long serialVersionUID = 956187325117395404L;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    Account account;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    Permission permission;
}
