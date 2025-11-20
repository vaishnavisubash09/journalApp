package com.engineeringdigest.journalApp.service;


import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String name) {
        return userRepository.findByUsername(name);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

}
