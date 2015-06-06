package com.banner.model;

import java.util.List;
import java.util.Map;


public interface BannerDAO_interface {
	public  void insert(BannerVO dishVO);
	public  void delete(Integer dish_no);
	public  void update(BannerVO dishVO);
	public BannerVO findByPrimaryKey(Integer banner_no);
    public List<BannerVO> getAll();

}
