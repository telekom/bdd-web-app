package org.jbehave.webapp.taxi.repositories;

import org.jbehave.webapp.taxi.domain.Reservation;
import org.jbehave.webapp.taxi.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    List<Reservation> findByUser(User user);

    void deleteByUser(User user);

}
