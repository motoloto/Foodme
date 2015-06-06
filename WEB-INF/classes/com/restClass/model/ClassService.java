package com.restClass.model;

import java.sql.SQLException;
import java.util.List;

public class ClassService {

	private ClassDAO_interface dao;
	
	public ClassService()
	{
		dao=new ClassJNDIDAO();
	}
	
	public ClassVO addClass(String class_name)
	{
	    ClassVO vo=new ClassVO();
	 
	    vo.setClass_name(class_name);
	    dao.insert(vo);
	    return vo;
	}
	
	public ClassVO updateClass(Integer class_no,String class_name)
	{
		ClassVO vo=new ClassVO();
	    vo.setClass_no(class_no);
	    vo.setClass_name(class_name);
	    dao.update(vo);
	    return vo;	
	}
	
	public void deleteClass(Integer class_no) throws ClassNotFoundException, SQLException
	{
		dao.delete(class_no);
	}
	
	public ClassVO getOneEmp(Integer class_no) throws ClassNotFoundException, SQLException {
		return dao.findByPrimaryKey(class_no);
	}

	public List<ClassVO> getAll() throws ClassNotFoundException, SQLException {
		return dao.getAll();
	}
}
