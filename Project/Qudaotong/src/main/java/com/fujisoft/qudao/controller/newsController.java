package com.fujisoft.qudao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fujisoft.qudao.domain.News;
import com.fujisoft.qudao.service.NewsService;

import groovy.util.logging.Slf4j;

@Controller
public class newsController {

	@Autowired
	private NewsService newsService;

	// 渠道通界面新闻的获取
	@GetMapping("/news")
	String listNews(@ModelAttribute("News") News news, Model model) {
		List<News> newss = newsService.getNewsList(news);
		model.addAttribute("newss", newss);
		return "html/qudaotong";
	}

}
