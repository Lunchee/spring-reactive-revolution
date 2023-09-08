package lunchee.springreactive;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final ReservationRepository reservationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        Flux<Reservation> reservations = Flux
                .just("Tatiana", "Alexander", "Nyan-cat", "Pichu")
                .map(Reservation::new)
                .flatMap(reservationRepository::save);

        reservationRepository.deleteAll()
                .thenMany(reservations)
                .thenMany(reservationRepository.findAll())
                .doOnError(System.err::println)
                .subscribe(System.out::println);
    }
}
