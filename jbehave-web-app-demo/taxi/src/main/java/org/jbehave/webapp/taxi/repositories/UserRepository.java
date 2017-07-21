package org.jbehave.webapp.taxi.repositories;


import org.jbehave.webapp.taxi.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User getByUsername(String username);

    User findByUsername(String username);

}
