package com.daeliin.components.security.credentials.accountpermission;

import com.daeliin.components.domain.resource.PersistentResource;
import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.permission.Permission;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(of = {"account", "permission"}, callSuper = true)
@Table(name = "account_permission")
@Entity
public class AccountPermission extends PersistentResource implements Comparable<AccountPermission> {
    
    private static final long serialVersionUID = 956187325117395404L;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Account account;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Permission permission;

    public AccountPermission(Account account, Permission permission) {
        this.account = account;
        this.permission = permission;
    }
    
    @Override
    public int compareTo(AccountPermission other) {
        boolean accountsAreNotNull = this.account != null && other.account != null;
        
        if (this.equals(other)) {
            return 0;
        }
        
        if (accountsAreNotNull) {
            return this.account.compareTo(other.account);
        } else {
            return -1;
        }
    }
}
