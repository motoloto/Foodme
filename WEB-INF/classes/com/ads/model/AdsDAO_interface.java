package com.ads.model;

import java.util.List;

import com.banner.model.BannerVO;

public interface AdsDAO_interface {
	public  void insert(AdsVO adsVO);
	public  void delete(Integer ads_no);
	public  void update(AdsVO adsVO);
	public AdsVO findByPrimaryKey(Integer ads_no);
    public List<AdsVO> getAll();
}
