package de.telekom.test.bddwebapp.taxi.repositories;


import de.telekom.test.bddwebapp.taxi.repositories.domain.Registration;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface UserRepository extends CrudRepository<Registration, Integer> {

    Registration getByUsername(String username);

    Registration findByUsername(String username);

}
