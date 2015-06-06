package com.news.model;

import java.io.Serializable;
import java.sql.Date;

public class NewsVO implements Serializable {
	private Integer news_no;
	private Date news_time;
	private String news_title;
	private String news_cont;
	public Integer getNews_no() {
		return news_no;
	}
	public void setNews_no(Integer news_no) {
		this.news_no = news_no;
	}
	public Date getNews_time() {
		return news_time;
	}
	public void setNews_time(Date news_time) {
		this.news_time = news_time;
	}
	public String getNews_title() {
		return news_title;
	}
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	public String getNews_cont() {
		return news_cont;
	}
	public void setNews_cont(String news_cont) {
		this.news_cont = news_cont;
	}
	
	


}
