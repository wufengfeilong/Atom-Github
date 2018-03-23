package com.fujisoft.qudao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

	@GetMapping("/hi")
	public String test() {
		return "hello,qudaotong!";
	}

	// 这个访问url形式：http://localhost:5005/Qudaotong/hi/zhangfengwu
	@GetMapping("/hi/{name}")
	public String testOneParam1(@PathVariable("name") String name) {
		return "Hello " + name;
	}

	// 这个访问url形式：http://localhost:5005/Qudaotong/hello?name=zhangfengwu
	@GetMapping("/hello")
	public String testOneParam2(@RequestParam("name") String name) {
		return "Hello " + name;
	}

}
