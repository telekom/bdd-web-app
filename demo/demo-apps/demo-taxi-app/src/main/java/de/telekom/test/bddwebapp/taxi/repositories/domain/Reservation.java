package de.telekom.test.bddwebapp.taxi.repositories.domain;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue
    private Long reservationId;

    @ManyToOne
    @JoinColumn
    private Registration user;

    private String date;

    private String departure;

    private String earliestStartTime;

    private String destination;

    private String latestStartTime;

    private Date creationDate;

    private Date modificationDate;

}
