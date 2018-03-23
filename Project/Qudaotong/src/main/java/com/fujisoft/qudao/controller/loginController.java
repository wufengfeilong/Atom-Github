package com.fujisoft.qudao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fujisoft.qudao.domain.User;
import com.fujisoft.qudao.service.UserService;

@Controller
public class loginController {
	@Autowired
	private UserService userService;

	// 项目运行进入login界面
	@RequestMapping("/")
	String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	// 进入注册界面
	@RequestMapping("/register")
	String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	// 注册用户
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	@ResponseBody
	void registerUser(@RequestParam(value = "password", defaultValue = "0") String passwd,
			@RequestParam(value = "name", defaultValue = "0") String name, Model model) {
		userService.registerUser(passwd, name);
	}

	// 用户登录验证
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	@ResponseBody
	int userLogin(@RequestParam(value = "password", defaultValue = "0") String passwd,
			@RequestParam(value = "name", defaultValue = "0") String name, Model model) {
		boolean verify = userService.verifyUser(passwd, name);
		if (verify) {
			model.addAttribute("name", name);
			model.addAttribute("passwd", passwd);
			return 0;
		} else {
			return 1;
		}

	}

	// 登录后进入主界面
	@RequestMapping("/userLoginPlus")
	String userlogin(@RequestParam(value = "username", defaultValue = "0") String name, Model model) {
		model.addAttribute("name", name);
		return "main";
	}

	// 找回密码
	@RequestMapping("/findPassword")
	String findPassword(Model model) {
		model.addAttribute("user", new User());
		return "findpassword";
	}

}
