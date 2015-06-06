package com.banner.model;

import java.sql.Date;

public class BannerVO {
	private Integer banner_no;
	private Integer rest_no;
	private String banner_title;
	private String banner_cont;
	private byte[] banner_pic;
	private Date banner_dl;
	public Integer getBanner_no() {
		return banner_no;
	}
	public void setBanner_no(Integer banner_no) {
		this.banner_no = banner_no;
	}
	public Integer getRest_no() {
		return rest_no;
	}
	public void setRest_no(Integer rest_no) {
		this.rest_no = rest_no;
	}
	public String getBanner_title() {
		return banner_title;
	}
	public void setBanner_title(String banner_title) {
		this.banner_title = banner_title;
	}
	public String getBanner_cont() {
		return banner_cont;
	}
	public void setBanner_cont(String banner_cont) {
		this.banner_cont = banner_cont;
	}
	public byte[] getBanner_pic() {
		return banner_pic;
	}
	public void setBanner_pic(byte[] banner_pic) {
		this.banner_pic = banner_pic;
	}
	public Date getBanner_dl() {
		return banner_dl;
	}
	public void setBanner_dl(Date banner_dl) {
		this.banner_dl = banner_dl;
	}
}
