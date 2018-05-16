package com.daeliin.components.cms.credentials.account;

import com.daeliin.components.persistence.resource.repository.ResourceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AccountRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private AccountRepository accountRepository;

    @Test
    public void shouldExtendResourceRepository() {
        assertThat(accountRepository.getClass().getSuperclass().getClass()).isEqualTo(ResourceRepository.class.getClass());
    }
}
