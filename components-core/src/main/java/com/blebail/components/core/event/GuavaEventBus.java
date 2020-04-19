package com.blebail.components.core.event;

import org.springframework.stereotype.Component;

@Component
public class GuavaEventBus implements EventBus {

    private final com.google.common.eventbus.EventBus bus;

    public GuavaEventBus() {
        this.bus = new com.google.common.eventbus.EventBus();
    }

    @Override
    public void register(Object listener) {
        this.bus.register(listener);
    }

    @Override
    public void unregister(Object listener) {
        this.bus.unregister(listener);
    }

    @Override
    public void post(Object event) {
        this.bus.post(event);
    }
}
