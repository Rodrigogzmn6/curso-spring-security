package com.rodrigoguzman.spring_security.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.spring_security.model.UserSec;

public interface IUserService {
    UserSec save(UserSec user);

    List<UserSec> findAll();

    Optional<UserSec> findById(Long id);

    Optional<UserSec> findByUsername(String username);

    UserSec update(UserSec user);

    void deleteById(Long id);

    String encryptPassword(String password);
}
