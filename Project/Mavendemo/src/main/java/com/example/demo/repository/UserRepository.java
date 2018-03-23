package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bean.User;

/** 
* create by 張風武 
* 2017/07/13 9:06:00 
*/
public interface UserRepository extends JpaRepository<User, Integer>{
    
}
