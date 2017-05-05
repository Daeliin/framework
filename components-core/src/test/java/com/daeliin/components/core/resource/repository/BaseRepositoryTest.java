package com.daeliin.components.core.resource.repository;


import com.daeliin.components.core.Application;
import com.daeliin.components.core.fake.UuidPersistentResourceBaseRepository;
import com.daeliin.components.core.sql.QUuidPersistentResource;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class BaseRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    private UuidPersistentResourceBaseRepository repository;

    @Test
    public void shouldProvideTheRowPath() {
        assertThat(repository.rowPath()).isEqualTo(QUuidPersistentResource.uuidPersistentResource);
    }
}
