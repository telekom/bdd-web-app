package org.jbehave.webapp.taxi.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "RESERVATION_ID_SEQ")
    @SequenceGenerator(name = "RESERVATION_ID_SEQ", sequenceName = "RESERVATION_ID_SEQ", allocationSize = 100)
    private Integer reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private String departure;

    private String earliestStartTime;

    private String destination;

    private String latestStartTime;

    private Date creationDate;

    private Date modificationDate;

}