package de.telekom.test.bddwebapp.taxi.service;

import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationVO;
import de.telekom.test.bddwebapp.taxi.domain.Reservation;
import de.telekom.test.bddwebapp.taxi.domain.User;
import de.telekom.test.bddwebapp.taxi.repositories.ReservationRepository;
import de.telekom.test.bddwebapp.taxi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void saveReservation(String username, ReservationVO reservationVO) {
        var user = getUser(username);

        // only one reservation at a time
        reservationRepository.deleteByUser(user);

        var reservation = new Reservation();
        copyProperties(reservationVO, reservation);
        reservation.setUser(user);
        reservation.setCreationDate(new Date());
        reservationRepository.save(reservation);
    }

    public ReservationVO getReservation(String username) {
        var user = getUser(username);
        var reservations = reservationRepository.findByUser(user);
        if (reservations.isEmpty()) {
            return null;
        }
        var reservation = reservations.get(0);
        var reservationVO = new ReservationVO();
        copyProperties(reservation, reservationVO);
        return reservationVO;
    }

    private User getUser(String username) {
        return userRepository.getByUsername(username);
    }

}
