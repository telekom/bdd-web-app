package de.telekom.test.bddwebapp.taxi.service;

import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationVO;
import de.telekom.test.bddwebapp.taxi.domain.Reservation;
import de.telekom.test.bddwebapp.taxi.domain.User;
import de.telekom.test.bddwebapp.taxi.repositories.ReservationRepository;
import de.telekom.test.bddwebapp.taxi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Service
public class ReservationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public void saveReservation(String username, ReservationVO reservationVO) {
        User user = getUser(username);

        // only one reservation at a time
        reservationRepository.deleteByUser(user);

        Reservation reservation = new Reservation();
        copyProperties(reservationVO, reservation);
        reservation.setUser(user);
        reservation.setCreationDate(new Date());
        reservationRepository.save(reservation);
    }

    public ReservationVO getReservation(String username) {
        User user = getUser(username);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        if (reservations.isEmpty()) {
            return null;
        }
        Reservation reservation = reservations.get(0);
        ReservationVO reservationVO = new ReservationVO();
        copyProperties(reservation, reservationVO);
        return reservationVO;
    }

    private User getUser(String username) {
        return userRepository.getByUsername(username);
    }

}
