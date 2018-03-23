package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.bean.User;
import com.example.demo.repository.UserRepository;

/** 
* create by 張風武 
* 2017/09/04 11:08:12 
*/
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public void save(User user){
        userRepository.save(user);
    }
}
