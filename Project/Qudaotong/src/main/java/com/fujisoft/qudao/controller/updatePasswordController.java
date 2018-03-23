package com.fujisoft.qudao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fujisoft.qudao.domain.User;

@Controller
public class updatePasswordController {
	@RequestMapping("/updatePassword")
	String updatePassword(Model model) {
		model.addAttribute("user", new User());
		return "html/SystemManagement";
	}
}
