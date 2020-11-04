package com.blebail.components.cms.credentials.permission;

import com.blebail.components.cms.sql.BAccountPermission;
import com.blebail.components.cms.sql.BPermission;
import com.blebail.components.cms.sql.QAccountPermission;
import com.blebail.components.cms.sql.QPermission;
import com.blebail.components.persistence.resource.repository.SpringCrudRepository;
import com.blebail.querydsl.crud.commons.resource.IdentifiableQDSLResource;
import com.google.common.base.Strings;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Loads permission in memory, and caches it.
 */
@Transactional
@Component
public class PermissionRepository extends SpringCrudRepository<QPermission, BPermission, String> {

    @Inject
    public PermissionRepository(SQLQueryFactory queryFactory) {
        super(new IdentifiableQDSLResource<>(QPermission.permission, QPermission.permission.id, BPermission::getId), queryFactory);
    }

    public Collection<BPermission> findForAccount(String accountId) {
        if (Strings.isNullOrEmpty(accountId)) {
            return new ArrayList<>();
        }

        return queryFactory.select(QPermission.permission)
                .from(QPermission.permission)
                .join(QAccountPermission.accountPermission)
                .on(QAccountPermission.accountPermission.permissionId.eq(QPermission.permission.id))
                .where(QAccountPermission.accountPermission.accountId.eq(accountId))
                .orderBy(QPermission.permission.name.asc())
                .fetch();
    }

    public void addToAccount(String accountId, String permissionId) {
        BAccountPermission accountPermission= new BAccountPermission(accountId, permissionId);

        Set<String> accountPermissions = findForAccount(accountId)
                .stream()
                .map(BPermission::getId)
                .collect(toSet());

        if (!accountPermissions.contains(permissionId)) {
            queryFactory.insert(QAccountPermission.accountPermission)
                    .populate(accountPermission)
                    .execute();
        }
    }

    public void deleteForAccount(String accountId) {
        queryFactory.delete(QAccountPermission.accountPermission)
                .where(QAccountPermission.accountPermission.accountId.eq(accountId))
                .execute();
    }

    public void deleteForAccount(String accountId, String permissionId) {
        queryFactory.delete(QAccountPermission.accountPermission)
                .where(QAccountPermission.accountPermission.accountId.eq(accountId)
                .and(QAccountPermission.accountPermission.permissionId.eq(permissionId)))
                .execute();
    }

    public boolean isUsed(String permissionId) {
        return queryFactory.select(QAccountPermission.accountPermission)
                .from(QAccountPermission.accountPermission)
                .where(QAccountPermission.accountPermission.permissionId.eq(permissionId))
                .fetchCount() > 0;
    }
}
