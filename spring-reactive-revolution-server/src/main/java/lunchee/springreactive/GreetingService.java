package lunchee.springreactive;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Controller
public class GreetingService {

    @MessageMapping("greetings")
    public Flux<GreetingResponse> greet(GreetingRequest request) {
        return Flux
                .fromStream(Stream.generate(() -> new GreetingResponse("Hello %s @ %s.".formatted(request.getName(), Instant.now()))))
                .delayElements(Duration.ofSeconds(1));
    }
}
