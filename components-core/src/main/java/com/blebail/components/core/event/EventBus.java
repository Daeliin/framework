package com.blebail.components.core.event;

public interface EventBus {

    void register(Object listener);

    void unregister(Object listener);

    void post(Object event);
}
