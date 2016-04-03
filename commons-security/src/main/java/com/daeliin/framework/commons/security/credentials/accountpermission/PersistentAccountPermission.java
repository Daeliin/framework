package com.daeliin.framework.commons.security.credentials.accountpermission;

import com.daeliin.framework.commons.model.LongIdPersistentResource;
import com.daeliin.framework.commons.security.credentials.account.Account;
import com.daeliin.framework.commons.security.credentials.permission.Permission;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"account", "permission"}, callSuper = true)
@MappedSuperclass
@Table(name = "account_permission")
@Entity
public class PersistentAccountPermission extends LongIdPersistentResource implements AccountPermission {
    
    private static final long serialVersionUID = 956187325117395404L;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    Account account;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    Permission permission;
}
