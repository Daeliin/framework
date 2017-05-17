package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.security.library.PermissionLibrary;
import com.daeliin.components.security.sql.QPermission;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class PermissionServiceTest {

//    @Before
//    public void setUp() {
//        permissionRepository.invalidateCache();
//    }
//    @Test
//    public void shouldPersistAPermission() {
//        Permission newPermission = new Permission("NEW");
//
//        int rowCountBeforePersist = countRows();
//
//        Permission persistedPermission = permissionRepository.save(newPermission);
//
//        int rowCountAfterPersist = countRows();
//
//        assertThat(persistedPermission).isEqualTo(newPermission);
//        assertThat(rowCountAfterPersist).isEqualTo(rowCountBeforePersist + 1);
//    }
//
//    @Test
//    public void shouldReturnNull_whenFindingNull() {
//        assertThat(permissionRepository.findOne(null)).isNull();
//    }
//
//    @Test
//    public void shouldReturnNull_whenFindingEmptyString() {
//        assertThat(permissionRepository.findOne("")).isNull();
//    }
//
//    @Test
//    public void shouldFindAPermission() {
//        assertThat(permissionRepository.findOne(PermissionLibrary.admin().name)).isEqualTo(PermissionLibrary.admin());
//    }
//
//    @Test
//    public void shouldFindAllPermission() {
//        assertThat(permissionRepository.findAll()).containsOnly(
//                PermissionLibrary.admin(),
//                PermissionLibrary.user(),
//                PermissionLibrary.moderator());
//    }
//
//    @Test
//    public void shouldNotDeleteNull() {
//        int rowCountBeforePersist = countRows();
//
//        boolean delete = permissionRepository.delete(null);
//
//        int rowCountAfterPersist = countRows();
//
//        assertThat(delete).isFalse();
//        assertThat(rowCountAfterPersist).isEqualTo(rowCountBeforePersist);
//    }
//
//    @Test
//    public void shouldDeleteAPermission() {
//        int rowCountBeforePersist = countRows();
//
//        boolean delete = permissionRepository.delete(PermissionLibrary.moderator().name);
//
//        int rowCountAfterPersist = countRows();
//
//        assertThat(delete).isTrue();
//        assertThat(rowCountAfterPersist).isEqualTo(rowCountBeforePersist - 1);
//    }
//
//    private int countRows() {
//        return countRowsInTable(QPermission.permission.getTableName());
//    }
}
