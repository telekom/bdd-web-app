package collectivetaxi.service;

import collectivetaxi.controller.RegistrationVO;
import collectivetaxi.domain.User;
import collectivetaxi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    public void register(RegistrationVO registration) {
        User user = new User();
        copyProperties(registration, user);
        user.setCreationDate(new Date());
        userRepository.save(user);
    }

}
