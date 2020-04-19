package com.blebail.components.core.event;

import com.google.common.eventbus.Subscribe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public final class EventBusIT {

    @Inject
    private EventBus tested;

    @Test
    void name() {
        SomeListener someListener = new SomeListener();

        tested.register(someListener);
        assertThat(someListener.lastEventMessage).isNull();

        tested.post(new SomeEvent("someMessage1"));
        assertThat(someListener.lastEventMessage).isEqualTo("someMessage1");

        tested.post(new SomeEvent("someMessage2"));
        assertThat(someListener.lastEventMessage).isEqualTo("someMessage2");
    }

    private static final class SomeEvent {

        public final String message;

        public SomeEvent(String message) {
            this.message = message;
        }
    }

    private static final class SomeListener {

        public String lastEventMessage;

        @Subscribe
        public void recordSomeEvent(SomeEvent someEvent) {
            this.lastEventMessage = someEvent.message;
        }
    }
}