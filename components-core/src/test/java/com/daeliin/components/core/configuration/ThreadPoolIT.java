package com.daeliin.components.core.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public final class ThreadPoolIT {

    @Inject
    private ThreadPool tested;

    @Test
    public void shouldHaveACorePoolSizeOf4() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor)tested.taskExecutor();

        assertThat(taskExecutor.getCorePoolSize()).isEqualTo(4);
    }

    @Test
    public void shouldHaveAMaxPoolSizeOf4() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor)tested.taskExecutor();

        assertThat(taskExecutor.getMaxPoolSize()).isEqualTo(4);
    }

    @Test
    public void shouldHaveAPrefix() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor)tested.taskExecutor();

        assertThat(taskExecutor.getThreadNamePrefix()).isEqualTo("java-components-spring-task-executor");
    }
}