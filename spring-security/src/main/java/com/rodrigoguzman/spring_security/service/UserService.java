package com.rodrigoguzman.spring_security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rodrigoguzman.spring_security.model.UserSec;
import com.rodrigoguzman.spring_security.repository.IUserRepository;

@Service
public class UserService implements IUserService {
    final IUserRepository userRepository;

    UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserSec save(UserSec user) {
        return userRepository.save(user);
    }

    @Override
    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public List<UserSec> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserSec> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserSec update(UserSec user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
