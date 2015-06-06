package com.intro.model;

import java.sql.SQLException;
import java.util.List;

public class IntroService {
	
	private IntroDAO_interface dao;
	
	public IntroService()
	{
		dao=new IntroDAO();
	}
	
	public IntroVO addIntroduction(Integer rest_no,String intro_cont,
			byte[] intro_pic, Integer intro_ord)
	{
		IntroVO vo=new IntroVO();
	    vo.setRest_no(rest_no);
	    vo.setIntro_pic(intro_pic);
	    vo.setIntro_cont(intro_cont);
	    vo.setIntro_ord(intro_ord);
	    dao.insert(vo);
	    return vo;
	}
	
	public void updateIntroduction(IntroVO vo)
	{
	    dao.update(vo);
	}
	
	public void deleteIntroduction(Integer intro_no) throws ClassNotFoundException, SQLException
	{
		dao.delete(intro_no);
	}
	
	public IntroVO getOneIntroduction(Integer intro_no) throws ClassNotFoundException, SQLException {
		return dao.findByPrimaryKey(intro_no);
	}

	public List<IntroVO> getAll() throws ClassNotFoundException, SQLException {
		return dao.getAll();
	}
	public List<IntroVO> getAllByARest(Integer rest_no){
		return dao.findByForeignKey(rest_no);
	}
	public void updateOrder(Integer rest_no,Integer intro_no,Integer order_no){
		dao.updateOrder(rest_no,intro_no,order_no);
	}
}
