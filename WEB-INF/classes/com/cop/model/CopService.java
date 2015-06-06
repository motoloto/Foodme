package com.cop.model;


import java.sql.Date;
import java.util.List;
import java.util.Map;


public class CopService {

	private CopDAO_interface dao;

	public CopService() {
		dao = new CopDAO();
	}

	public CopVO addCop( 
			  Integer rest_no ,
			  String  cop_name ,
			  String  cop_content, 
			  Integer cop_orlprice, 
			  Integer cop_price, 
			  Date    cop_dl, 
			  String  cop_state, 
			  Date    cop_date, 
			  Integer cop_circu, 
			  Integer cop_selamt ) {

		CopVO copVO = new CopVO();

		copVO.setRest_no(rest_no);
		copVO.setCop_name(cop_name);
		copVO.setCop_content(cop_content);
		copVO.setCop_orlprice(cop_orlprice);
		copVO.setCop_price(cop_price);
		copVO.setCop_dl(cop_dl);
		copVO.setCop_state(cop_state);
		copVO.setCop_date(cop_date);
		copVO.setCop_circu(cop_circu);
		copVO.setCop_selamt(cop_selamt);
		
		
		dao.insert(copVO);

		return copVO;
	}

	public CopVO updateCop(
			  Integer cop_no,
			  Integer rest_no ,
			  String  cop_name ,
			  String  cop_content, 
			  Integer cop_orlprice, 
			  Integer cop_price, 
			  Date    cop_dl, 
			  String  cop_state, 
			  Date    cop_date, 
			  Integer cop_circu, 
			  Integer cop_selamt) {

		CopVO copVO = new CopVO();

		copVO.setCop_no(cop_no);
		copVO.setRest_no(rest_no);
		copVO.setCop_name(cop_name);
		copVO.setCop_content(cop_content);
		copVO.setCop_orlprice(cop_orlprice);
		copVO.setCop_price(cop_price);
		copVO.setCop_dl(cop_dl);
		copVO.setCop_state(cop_state);
		copVO.setCop_date(cop_date);
		copVO.setCop_circu(cop_circu);
		copVO.setCop_selamt(cop_selamt);
		dao.update(copVO);

		return copVO;
	}
	public CopVO updateCop(CopVO copVO){
		
		dao.update(copVO);
		
		return copVO;
	}

	public void deleteCop(Integer cop_no) {
		dao.delete(cop_no);
	}

	public CopVO getOneCop(Integer cop_no) {
		return dao.findByPrimaryKey(cop_no);
	}
	
	/*查詢此餐廳正在上架的餐劵*/
	public CopVO getOneCopOnState(Integer rest_no) {
		return dao.findCopByRest_no(rest_no);
	}

	/*查出最熱賣的餐劵*/
	public CopVO getHotSaleCop() {
		return dao.findHotSaleCop();
	}
	
	/*查出最新發行的餐劵*/
	public CopVO getLatestCop() {
		return dao.findLatestCop();
	}
	public List<CopVO> getAll() {
		return dao.getAll();
	}
	public List<CopVO> getCopByRest_no(Integer rest_no) {
		return dao.getCopByRest_no(rest_no);
	}
	
	public List<CopVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	public CopVO getOneCopByOdr(Map<String, String[]> map) {
		return dao.getOneCopByOdr(map);
	}
}

