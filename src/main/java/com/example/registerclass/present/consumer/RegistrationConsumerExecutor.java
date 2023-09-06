package com.example.registerclass.present.consumer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationConsumerExecutor {
    final RegistrationConsumer registrationConsumer;
    final ThreadPoolTaskExecutor taskExecutor;


    @PostConstruct
    public void init() {
        registrationConsumer.init();
        taskExecutor.execute(registrationConsumer);
    }

    @PreDestroy
    public void preDestroy() {
        registrationConsumer.shutdown();
    }
}
