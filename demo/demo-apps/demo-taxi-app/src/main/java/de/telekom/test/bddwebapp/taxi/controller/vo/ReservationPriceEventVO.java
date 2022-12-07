package de.telekom.test.bddwebapp.taxi.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public record ReservationPriceEventVO (String message, List<ReservationPriceEntryVO> reservationPrices){
}
