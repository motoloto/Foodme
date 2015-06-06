package com.news.model;

import java.sql.Date;
import java.util.List;


public class NewsService {
	private NewsDAO dao;
	
	public NewsService(){
		dao=new NewsDAO();
	}
	
	public NewsVO addNews( java.sql.Date  news_time, String news_title,String news_cont  ){
		NewsVO newsVO= new NewsVO();
		newsVO.setNews_time(news_time);
		newsVO.setNews_title(news_title);
		newsVO.setNews_cont(news_cont);

		dao.insert(newsVO);
		return newsVO;
	}
	public List<NewsVO> getAll(){
		return dao.getAll();
		
	}
	public void deleteNews(int news_no) {
		dao.delete(news_no);
	}


	public NewsVO getOneForUpdate(Integer news_no) {
		return dao.findByPrimaryKey(news_no);
	}

	public void updateNews(NewsVO newsVO) {
		
		  dao.update(newsVO);
	}

	

}
