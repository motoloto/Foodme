package com.ads.model;

import java.sql.Date;

public class AdsVO {
	private Integer ads_no;
	private Integer rest_no;
	private String ads_title;
	private byte[] ads_pic;
	private Date ads_dl;
	public Integer getAds_no() {
		return ads_no;
	}
	public void setAds_no(Integer ads_no) {
		this.ads_no = ads_no;
	}
	public Integer getRest_no() {
		return rest_no;
	}
	public void setRest_no(Integer rest_no) {
		this.rest_no = rest_no;
	}
	public String getAds_title() {
		return ads_title;
	}
	public void setAds_title(String ads_title) {
		this.ads_title = ads_title;
	}
	public byte[] getAds_pic() {
		return ads_pic;
	}
	public void setAds_pic(byte[] ads_pic) {
		this.ads_pic = ads_pic;
	}
	public Date getAds_dl() {
		return ads_dl;
	}
	public void setAds_dl(Date ads_dl) {
		this.ads_dl = ads_dl;
	}
	
}
