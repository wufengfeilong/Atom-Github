package com.fujisoft.qudao.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;

import com.fujisoft.qudao.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findByName(String name);

	public User findByNameAndPassword(String name, String password);

	@Query("from User u where u.name=:name")
	public List<User> findUser(@Param("name") String name);

	public String save(String name);

}
