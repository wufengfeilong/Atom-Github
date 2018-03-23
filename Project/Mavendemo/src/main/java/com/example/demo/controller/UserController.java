package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@RestController
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/saveUser")
    public void save(
            @RequestParam(value = "name",required = false,defaultValue = "no name") String name,
            @RequestParam(value = "pwd",required = false,defaultValue = "no name") String pwd,
            @RequestParam(value = "tel",required = false,defaultValue = "no name") String tel){
        userService.save(new User(name,pwd,tel));
    }
    
    @GetMapping("/getById/{id}")
    public User getById(@PathVariable("id") int id){
        return userRepository.findOne(id);
    }
}
