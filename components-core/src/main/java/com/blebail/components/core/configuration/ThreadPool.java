package com.blebail.components.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPool {

    private static final String JAVA_COMPONENTS_SPRING_TASK_EXECUTOR_PREFIX = "java-components-spring-task-executor";

    @Value("${blebail.threadpool.coreSize}")
    private int corePoolSize;

    @Value("${blebail.threadpool.maxSize}")
    private int maxPoolSize;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setThreadNamePrefix(JAVA_COMPONENTS_SPRING_TASK_EXECUTOR_PREFIX);
        executor.initialize();

        return executor;
    }
}
