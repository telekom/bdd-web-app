package de.telekom.test.bddwebapp.taxi.repositories;


import de.telekom.test.bddwebapp.taxi.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User getByUsername(String username);

    User findByUsername(String username);

}
