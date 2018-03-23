package com.fujisoft.qudao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fujisoft.qudao.domain.News;
import com.fujisoft.qudao.domain.User;
import com.fujisoft.qudao.repository.NewsRepository;
import com.fujisoft.qudao.repository.UserRepository;

import groovy.util.logging.Slf4j;
@Slf4j
@Service
public class NewsService {
	@Autowired
	private NewsRepository newsRepository;

	public List<News> getNewsList(News news) {
		return this.newsRepository.findFirstByOrderByIdDesc();
	}

}
