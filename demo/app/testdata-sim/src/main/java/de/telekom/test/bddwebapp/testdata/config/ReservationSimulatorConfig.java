package de.telekom.test.bddwebapp.testdata.config;

import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationNotPossibleException;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Service
public class ReservationSimulatorConfig {

    @Setter
    @Getter
    private Optional<ReservationVO> currentReservation;

    public ReservationVO reserve(ReservationVO reservation) {
        return currentReservation.orElseThrow(() -> new ReservationNotPossibleException());
    }

}
