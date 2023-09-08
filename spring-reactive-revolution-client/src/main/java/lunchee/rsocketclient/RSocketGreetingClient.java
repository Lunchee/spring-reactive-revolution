package lunchee.rsocketclient;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RSocketGreetingClient {

    private final RSocketRequester rSocketRequester;

    @EventListener(ApplicationReadyEvent.class)
    public void sendGreetingRequest() {
        rSocketRequester
                .route("greetings")
                .data(new GreetingRequest("Pecha"))
                .retrieveFlux(GreetingResponse.class)
                .subscribe(System.out::println);
    }
}
