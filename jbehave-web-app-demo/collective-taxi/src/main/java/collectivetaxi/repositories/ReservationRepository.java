package collectivetaxi.repositories;

import collectivetaxi.domain.Reservation;
import collectivetaxi.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    List<Reservation> findByUser(User user);

}
