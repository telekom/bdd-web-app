package de.telekom.test.bddwebapp.taxi.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue
    private Long reservationId;

    @ManyToOne
    @JoinColumn
    private User user;

    private String date;

    private String departure;

    private String earliestStartTime;

    private String destination;

    private String latestStartTime;

    private Date creationDate;

    private Date modificationDate;

}
