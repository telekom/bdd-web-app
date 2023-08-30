package de.telekom.test.bddwebapp.taxi.controller.vo;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public record ReservationPriceEntryVO(String startTime, String endTime,
                                      String price, String passengers) {
}
