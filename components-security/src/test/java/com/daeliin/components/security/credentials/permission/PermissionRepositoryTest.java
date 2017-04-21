package com.daeliin.components.security.credentials.permission;

import com.daeliin.components.core.resource.repository.Repository;
import com.daeliin.components.security.Application;
import com.daeliin.components.security.library.PermissionLibrary;
import com.daeliin.components.security.sql.QPermission;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class PermissionRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private PermissionRepository permissionRepository;

    @Before
    public void setUp() {
        assertThat(countRows()).isEqualTo(2);
    }

    @Test
    public void shouldExtendRepository() {
        assertThat(permissionRepository.getClass().getSuperclass().getClass()).isEqualTo(Repository.class.getClass());
    }

    @Test
    public void shouldPersistAPermission() {
        Permission newPermission = new Permission("MODERATOR");

        int rowCountBeforePersist = countRows();

        Permission persistedPermission = permissionRepository.save(newPermission);

        int rowCountAfterPersist = countRows();

        assertThat(persistedPermission).isEqualTo(newPermission);
        assertThat(rowCountAfterPersist).isEqualTo(rowCountBeforePersist + 1);
    }

    @Test
    public void shouldReturnNull_whenFindingNull() {
        assertThat(permissionRepository.findOne(null)).isNull();
    }

    @Test
    public void shouldReturnNull_whenFindingEmptyString() {
        assertThat(permissionRepository.findOne("")).isNull();
    }

    @Test
    public void shouldFindAPermission() {
        assertThat(permissionRepository.findOne(PermissionLibrary.admin().label)).isEqualTo(PermissionLibrary.admin());
    }

    @Test
    public void shouldFindAllPermission() {
        assertThat(permissionRepository.findAll()).containsOnly(PermissionLibrary.admin(), PermissionLibrary.user());
    }

    @Test
    public void shouldNotDeleteNull() {
        int rowCountBeforePersist = countRows();

        boolean delete = permissionRepository.delete(null);

        int rowCountAfterPersist = countRows();

        assertThat(delete).isFalse();
        assertThat(rowCountAfterPersist).isEqualTo(rowCountBeforePersist);
    }

    @Test
    public void shouldDeleteAPermission() {
        int rowCountBeforePersist = countRows();

        boolean delete = permissionRepository.delete(PermissionLibrary.admin().label);

        int rowCountAfterPersist = countRows();

        assertThat(delete).isTrue();
        assertThat(rowCountAfterPersist).isEqualTo(rowCountBeforePersist - 1);
    }

    private int countRows() {
        return countRowsInTable(QPermission.permission.getTableName());
    }
}
