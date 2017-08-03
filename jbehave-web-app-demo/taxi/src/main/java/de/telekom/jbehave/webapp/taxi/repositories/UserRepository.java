package de.telekom.jbehave.webapp.taxi.repositories;


import de.telekom.jbehave.webapp.taxi.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User getByUsername(String username);

    User findByUsername(String username);

}
