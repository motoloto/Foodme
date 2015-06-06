package com.intro.model;

import java.io.Serializable;

public class IntroVO implements Serializable{
    private Integer intro_no;
    private Integer rest_no;
    private byte[] intro_pic;
    private String intro_cont;
    private Integer intro_ord;
  
    
	public Integer getIntro_no() {
		return intro_no;
	}
	public void setIntro_no(Integer intro_no) {
		this.intro_no = intro_no;
	}
	public Integer getRest_no() {
		return rest_no;
	}
	public void setRest_no(Integer rest_no) {
		this.rest_no = rest_no;
	}
	public byte[] getIntro_pic() {
		return intro_pic;
	}
	public void setIntro_pic(byte[] intro_pic) {
		this.intro_pic = intro_pic;
	}
	public String getIntro_cont() {
		return intro_cont;
	}
	public void setIntro_cont(String intro_cont) {
		this.intro_cont = intro_cont;
	}
	public Integer getIntro_ord() {
		return intro_ord;
	}
	public void setIntro_ord(Integer intro_ord) {
		this.intro_ord = intro_ord;
	}
 
    
}
