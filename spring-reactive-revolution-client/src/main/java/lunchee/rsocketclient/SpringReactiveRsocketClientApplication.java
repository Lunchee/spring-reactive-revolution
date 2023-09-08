package lunchee.rsocketclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;

@SpringBootApplication
public class SpringReactiveRsocketClientApplication {

	@Bean
	public RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
		return builder.tcp("localhost", 8888);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveRsocketClientApplication.class, args);
	}
}
