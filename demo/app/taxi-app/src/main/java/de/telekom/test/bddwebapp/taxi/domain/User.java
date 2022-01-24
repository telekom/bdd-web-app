package de.telekom.test.bddwebapp.taxi.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(unique = true)
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Date creationDate;

    private Date modificationDate;

}
