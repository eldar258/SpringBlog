package com.example.blog.service;

import com.example.blog.domain.User;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User getById(Long id) {
        return userRepository.getById(id);
    }

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User create(String login, String email, String password) {
        var user = new User();

        user.setLogin(login);
        user.setEmail(email);

        user.setPassword(password);

        return userRepository.save(user);
    }

    public User update(Long id, String email) {
        var user = userRepository.getById(id);

        user.setEmail(email);

        return userRepository.save(user);
    }
}
