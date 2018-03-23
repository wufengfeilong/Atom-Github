package com.fujisoft.qudao.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class News {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String subtitle;

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	private String content;
	private String news_type;
	private String del_flg;

	public String getTitle() {
		return title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNews_type() {
		return news_type;
	}

	public void setNews_type(String news_type) {
		this.news_type = news_type;
	}

	public String getDel_flg() {
		return del_flg;
	}

	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
}
