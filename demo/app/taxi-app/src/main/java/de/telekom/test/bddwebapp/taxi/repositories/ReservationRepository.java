package de.telekom.test.bddwebapp.taxi.repositories;

import de.telekom.test.bddwebapp.taxi.domain.Reservation;
import de.telekom.test.bddwebapp.taxi.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    List<Reservation> findByUser(User user);

    void deleteByUser(User user);

}
