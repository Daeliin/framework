package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.repository.Repository;
import com.daeliin.components.security.sql.BPermission;
import com.daeliin.components.security.sql.QPermission;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.toSet;

/**
 * Loads permission in memory, and caches it.
 */
@Transactional
@Component
public class PermissionRepository extends Repository<BPermission> {

    private final Map<String, Permission> cache = Maps.newConcurrentMap();

    public PermissionRepository() {
        super(QPermission.permission);
    }

    public Permission save(Permission permission) {
        if (!cache.containsKey(permission.label)) {
            queryFactory.insert(rowPath)
                    .populate(new BPermission(permission.label))
                    .execute();
        }

        cache.put(permission.label, permission);

        return permission;
    }

    public Permission findOne(String label) {
        return cache.get(label);
    }

    @PostConstruct
    public Collection<Permission> findAll() {
        if (cache.isEmpty()) {
            queryFactory.select(rowPath)
                    .from(rowPath)
                    .fetch()
                    .stream().map(BPermission::getLabel)
                    .collect(toSet())
                    .forEach(label -> cache.put(label, new Permission(label)));
        }

        return cache.values();
    }

    public boolean delete(String label) {
        if (cache.containsKey(label)) {
            queryFactory.delete(rowPath)
                    .where(QPermission.permission.label.eq(label))
                    .execute();

            cache.remove(label);

            return true;
        }

        return false;
    }

    public void invalidateCache() {
        cache.clear();
    }
}
