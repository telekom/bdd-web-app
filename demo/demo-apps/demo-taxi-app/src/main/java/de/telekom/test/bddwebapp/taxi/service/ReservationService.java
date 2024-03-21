package de.telekom.test.bddwebapp.taxi.service;

import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationEventVO;
import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationPriceEventVO;
import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationVO;
import de.telekom.test.bddwebapp.taxi.repositories.ReservationRepository;
import de.telekom.test.bddwebapp.taxi.repositories.UserRepository;
import de.telekom.test.bddwebapp.taxi.repositories.domain.Registration;
import de.telekom.test.bddwebapp.taxi.repositories.domain.Reservation;
import de.telekom.test.bddwebapp.taxi.service.client.ReservationClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Optional;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationClient reservationClient;
    private final ModelMapper modelMapper = new ModelMapper();

    public Mono<ReservationPriceEventVO> createReservation(String username, ReservationVO reservationVO) {
        var user = getUser(username);

        var reservation = new Reservation();
        copyProperties(reservationVO, reservation);
        reservation.setUser(user);
        reservation.setCreationDate(new Date());
        reservationRepository.save(reservation);

        var reservationEvent = new ReservationEventVO();
        copyProperties(reservationVO, reservation);
        return reservationClient.createReservation(reservationEvent);
    }

    private Registration getUser(String username) {
        return userRepository.getByUsername(username);
    }

    public Optional<ReservationVO> getReservation(String username) {
        var user = getUser(username);
        var reservations = reservationRepository.findByUser(user);
        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationVO.class))
                .findFirst();
    }

    public Flux<ReservationPriceEventVO> getReservations(String username) {
        var user = getUser(username);
        return reservationClient.getReservations(user.getUserId());
    }

}
