package de.telekom.test.bddwebapp.taxi.repositories;

import de.telekom.test.bddwebapp.taxi.domain.Reservation;
import de.telekom.test.bddwebapp.taxi.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    List<Reservation> findByUser(User user);

    void deleteByUser(User user);

}
