package lunchee.springreactive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class WebConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ReservationRepository reservationRepository) {
        return route()
                .GET("/reservations", request -> ok().body(reservationRepository.findAll(), Reservation.class))
                .build();
    }

    @Bean
    public WebSocketHandler webSocketHandler(GreetingService greetingService) {
        return session -> {
            var chat = session
                    .receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .map(GreetingRequest::new)
                    .flatMap(greetingService::greet)
                    .map(GreetingResponse::getMessage)
                    .map(session::textMessage);

            return session.send(chat);
        };
    }

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler webSocketHandler) {
        return new SimpleUrlHandlerMapping(Map.of("/ws/greetings", webSocketHandler), 10);
    }
}
