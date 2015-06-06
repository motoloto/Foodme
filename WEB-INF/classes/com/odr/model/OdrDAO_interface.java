package com.odr.model;

import java.util.List;
import java.util.Map;


public interface OdrDAO_interface {

	 public int insert(OdrVO odrVO);
     public int update(OdrVO odrVO);
     public int delete(String ODR_NO);
     public OdrVO findByPrimaryKey(String ODR_NO);
     public List<OdrVO> getAll(); 
     public List<OdrVO> getAllPayedOrder(Map<String, String[]> map); 
     public List<OdrVO> getMemOdr(Integer mem_no);//只有搜尋出剩餘次數大於0的
     
     
	
}
