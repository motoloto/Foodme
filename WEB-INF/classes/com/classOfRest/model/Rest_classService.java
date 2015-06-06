package com.classOfRest.model;

import java.util.*;

public class Rest_classService {
	private Rest_classDAO_interface dao;
	
	public Rest_classService()
	{
		dao=new Rest_classDAO();
	}
	
	public void insertRest_class(Integer rest_no,Integer class_no)
	{
		Rest_classVO vo=new Rest_classVO();
		vo.setClass_no(class_no);
		vo.setRest_no(rest_no);
		
		dao.insert(vo);
	}
	
	public List<Rest_classVO> getRest_class(Integer rest_no)
	{		
		return dao.findByPrimaryKey(rest_no);
	}
	
	//一次可以更新多筆
	public void updateRest_class(Integer rest_no,Integer[] checkedClassNo)
	{
		Rest_classVO vo=new Rest_classVO();
		vo.setRest_no(rest_no);//更新餐廳分類之前先清除該餐廳所屬分類
		dao.deleteAll(vo);
		
		for(int i=0;i<checkedClassNo.length;i++)//將使用者輸入之餐廳分類加入
		{
			vo=new Rest_classVO();
			vo.setRest_no(rest_no);
			vo.setClass_no(checkedClassNo[i]);
			dao.insert(vo);
		}	
	}
	
	public List<Rest_classVO> getAll()
	{
		return dao.getAll();
	}
	
	public void deleteRest_class(Integer class_no,Integer rest_no)
	{
		Rest_classVO vo=new Rest_classVO();
		vo.setClass_no(class_no);
		vo.setRest_no(rest_no);
		dao.delete(vo);
	}
}
