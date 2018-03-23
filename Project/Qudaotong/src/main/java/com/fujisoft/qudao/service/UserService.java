package com.fujisoft.qudao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fujisoft.qudao.domain.User;
import com.fujisoft.qudao.repository.UserRepository;

@Controller
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public boolean verifyUser(String passwd, String name) {
		User sUser = userRepository.findByNameAndPassword(name, passwd);
		if (sUser != null) {
			return true;
		} else {
			return false;
		}
	}

	public void registerUser(String passwd, String name) {
		userRepository.save(new User(name, passwd));
	}

	public String login(String name) {
		return userRepository.save(name);
	}
}
