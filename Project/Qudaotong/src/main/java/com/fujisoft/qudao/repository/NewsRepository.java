package com.fujisoft.qudao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fujisoft.qudao.domain.News;

public interface NewsRepository extends JpaRepository<News, Long> {
	public List<News> findFirstByOrderByIdDesc();
}
