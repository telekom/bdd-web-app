package de.telekom.jbehave.webapp.taxi.repositories;


import de.telekom.jbehave.webapp.taxi.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User getByUsername(String username);

    User findByUsername(String username);

}
