package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
/** ServletComponentScan 这个注解是：过滤器和监听器用，如果没有配置可以不添加此注解 */
@ServletComponentScan
public class MavendemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MavendemoApplication.class, args);
	}
}
