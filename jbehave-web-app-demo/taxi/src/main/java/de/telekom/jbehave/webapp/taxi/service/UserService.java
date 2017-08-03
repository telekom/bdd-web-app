package de.telekom.jbehave.webapp.taxi.service;

import de.telekom.jbehave.webapp.taxi.controller.vo.RegistrationVO;
import de.telekom.jbehave.webapp.taxi.domain.User;
import de.telekom.jbehave.webapp.taxi.repositories.UserRepository;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(RegistrationVO registration) {
        // error in the control flow to demonstrate the reporting
        if ("fehler@test.de".equals(registration.getUsername())) {
            throw new RuntimeException("unexpected error");
        }
        User user = new User();
        copyProperties(registration, user);
        user.setPassword(sha3hash(registration.getPassword()));
        user.setCreationDate(new Date());
        userRepository.save(user);
    }

    public boolean isUsernameAndPasswordValid(String username, String password) {
        User user = findUserByUsername(username);
        return user != null && checkPassword(password, user);
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private boolean checkPassword(String password, User user) {
        if (!StringUtils.hasText(user.getPassword()) || !StringUtils.hasText(password)) {
            return false;
        }
        String hash = sha3hash(password);
        return user.getPassword().equals(hash);
    }

    private String sha3hash(String password) {
        try {
            SHA3.DigestSHA3 md = new SHA3.DigestSHA3(256);
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return Hex.toHexString(digest);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
