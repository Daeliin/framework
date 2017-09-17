package com.daeliin.components.security.membership.details;

import com.daeliin.components.security.credentials.account.Account;
import com.daeliin.components.security.credentials.permission.Permission;
import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.Set;

public final class AccountDetails {

    public final Account account;
    public final Set<Permission> permissions;

    public AccountDetails(Account account, Set<Permission> permissions) {
        this.account = Objects.requireNonNull(account);
        this.permissions = Objects.requireNonNull(permissions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDetails that = (AccountDetails) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, permissions);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("account", account)
                .add("permissions", permissions)
                .toString();
    }
}
