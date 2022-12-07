package de.telekom.test.bddwebapp.taxi.service;

import de.telekom.test.bddwebapp.taxi.controller.vo.RegistrationVO;
import de.telekom.test.bddwebapp.taxi.repositories.domain.Registration;
import de.telekom.test.bddwebapp.taxi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public void register(RegistrationVO registration) {
        // error in the control flow to demonstrate the reporting
        if ("error@test.de".equals(registration.username())) {
            throw new RuntimeException("unexpected error");
        }
        var user = new Registration();
        copyProperties(registration, user);
        user.setPassword(sha3hash(registration.password()));
        user.setCreationDate(new Date());
        userRepository.save(user);
    }

    public boolean isUsernameAndPasswordValid(String username, String password) {
        var user = findUserByUsername(username);
        return user != null && checkPassword(password, user);
    }

    private Registration findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private boolean checkPassword(String password, Registration user) {
        if (!StringUtils.hasText(user.getPassword()) || !StringUtils.hasText(password)) {
            return false;
        }
        var hash = sha3hash(password);
        return user.getPassword().equals(hash);
    }

    private String sha3hash(String password) {
        var md = new SHA3.DigestSHA3(256);
        md.update(password.getBytes(StandardCharsets.UTF_8));
        return Hex.toHexString(md.digest());
    }

}
