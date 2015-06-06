package com.news.model;
import java.util.*;

public interface NewsDAO_interface {
	public  void insert(NewsVO newsVO);
	public  void delete(Integer news_no);
	public  void update(NewsVO newsVO);
	public NewsVO findByPrimaryKey(Integer empno);
    public List<NewsVO> getAll();
     // �U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
     public List<NewsVO> getAll(Map<String, String[]> map); 
}
