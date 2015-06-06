package com.ads.model;

import java.sql.Date;
import java.util.List;


public class AdsService {
	private AdsDAO dao;
	
	public AdsService(){
		dao=new AdsDAO();
	}
	
	public AdsVO addAds(Integer rest_no, String ads_title, byte[] ads_pic, java.sql.Date ads_dl ){
		AdsVO adsVO= new AdsVO();
		adsVO.setRest_no(rest_no);
		adsVO.setAds_title(ads_title);
		adsVO.setAds_pic(ads_pic);
		adsVO.setAds_dl(ads_dl);
		dao.insert(adsVO);
		return adsVO;
	}
	public List<AdsVO> getAll(){
		return dao.getAll();
		
	}
	public void delete(int ads_no) {
		dao.delete(ads_no);
	}


	public AdsVO getOneAds(Integer ads_no) {
		return dao.findByPrimaryKey(ads_no);
	}

	public void updateAds(AdsVO adsVO) {
		
		  dao.update(adsVO);
	}

	

}
