package lunchee.springreactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReservationRepository extends ReactiveCrudRepository<Reservation, Integer> {
}
