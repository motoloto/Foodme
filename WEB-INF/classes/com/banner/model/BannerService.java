package com.banner.model;

import java.util.List;



public class BannerService {
	private BannerDAO dao;
	
	public BannerService(){
		dao=new BannerDAO();
	}
	
	public BannerVO addBanner(Integer rest_no, String banner_title, String banner_cont,byte[] banner_pic, java.sql.Date banner_dl ){
		BannerVO bannerVO= new BannerVO();
		bannerVO.setRest_no(rest_no);
		bannerVO.setBanner_title(banner_title);
		bannerVO.setBanner_cont(banner_cont);
		bannerVO.setBanner_pic(banner_pic);
		bannerVO.setBanner_dl(banner_dl);
		
		dao.insert(bannerVO);
		return bannerVO;
	}
	public List<BannerVO> getAll(){
		return dao.getAll();
		
	}
	public void deleteBanner(int banner_no){
		dao.delete(banner_no);
	}

	public BannerVO getOneBannerVO(Integer banner_no) {
		return dao.findByPrimaryKey(banner_no);
	}
	
	public void updateBanner(BannerVO bannerVO) {
		
		  dao.update(bannerVO);
	}
}
