package de.telekom.test.bddwebapp.taxi.domain;

import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reservation that = (Reservation) o;
        return reservationId != null && Objects.equals(reservationId, that.reservationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
