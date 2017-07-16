package collectivetaxi.service;

import collectivetaxi.controller.RegistrationVO;
import collectivetaxi.domain.User;
import collectivetaxi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    public void register(RegistrationVO registration) {
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setUsername(registration.getUsername());
        user.setCreationDate(new Date());
        userRepository.save(user);
    }

}
