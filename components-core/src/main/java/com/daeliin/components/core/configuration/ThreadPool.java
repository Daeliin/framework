package com.daeliin.components.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPool {

    private static final String JAVA_COMPONENTS_SPRING_TASK_EXECUTOR_PREFIX = "java-components-spring-task-executor";

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix(JAVA_COMPONENTS_SPRING_TASK_EXECUTOR_PREFIX);
        executor.initialize();

        return executor;
    }
}
