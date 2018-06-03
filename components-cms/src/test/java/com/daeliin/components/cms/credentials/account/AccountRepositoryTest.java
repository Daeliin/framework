package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountRepositoryTest {

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(AccountRepository.class.getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }
}
