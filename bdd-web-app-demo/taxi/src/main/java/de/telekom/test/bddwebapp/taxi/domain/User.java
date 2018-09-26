package de.telekom.test.bddwebapp.taxi.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_ID_SEQ")
    @SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ", allocationSize = 100)
    private Integer userId;

    @Column(unique = true)
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Date creationDate;

    private Date modificationDate;

}
