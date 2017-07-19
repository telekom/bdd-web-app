package collectivetaxi.repositories;


import collectivetaxi.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User getByUsername(String username);

    User findByUsername(String username);

}
