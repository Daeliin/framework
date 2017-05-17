package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.security.sql.BPermission;
import com.daeliin.components.security.sql.QPermission;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.toSet;

@Service
public final class PermissionService {

//    private final Map<String, Permission> cache = Maps.newConcurrentMap();
//    @Transactional
//    public Permission save(Permission permission) {
//        if (cache.isEmpty()) {
//            loadCache();
//        }
//
//        if (!cache.containsKey(permission.name)) {
//            queryFactory.insert(rowPath)
//                    .populate(new BPermission(permission.name))
//                    .execute();
//        }
//
//        cache.put(permission.name, permission);
//
//        return permission;
//    }
//
//    @Transactional(readOnly = true)
//    public Permission findOne(String label) {
//        if (cache.isEmpty()) {
//            loadCache();
//        }
//
//        if (Strings.isNullOrEmpty(label) || !cache.containsKey(label)) {
//            return null;
//        }
//
//        return cache.get(label);
//    }
//
//    @Transactional(readOnly = true)
//    public Collection<Permission> findAll() {
//        if (cache.isEmpty()) {
//            loadCache();
//        }
//
//        return cache.values();
//    }
//
//    @Transactional
//    public boolean delete(String label) {
//        if (cache.isEmpty()) {
//            loadCache();
//        }
//
//        if (Strings.isNullOrEmpty(label) || !cache.containsKey(label)) {
//            return false;
//        }
//
//        long nbDelete = queryFactory.delete(rowPath)
//                .where(QPermission.permission.label.eq(label))
//                .execute();
//
//        cache.remove(label);
//
//        return nbDelete == 1;
//    }
//
//    public void invalidateCache() {
//        cache.clear();
//    }
//
//    protected void loadCache() {
//        queryFactory.select(rowPath)
//                .from(rowPath)
//                .fetch()
//                .stream().map(BPermission::getLabel)
//                .collect(toSet())
//                .forEach(label -> cache.put(label, new Permission(label)));
//    }
}
