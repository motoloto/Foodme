package com.affi.model;

import java.sql.Date;

public class AffiVO implements java.io.Serializable {
	private Integer affi_no;
	private String bus_no;
	private String rest_name;
	private String rest_addr;
	private String rest_tel;
	private String rest_mobil;
	private byte[] rest_photo;
	private String rest_mail;
	private String rest_web;
	private String rest_intro;
	private String affi_state;

	public String getAffi_state() {
		return affi_state;
	}

	public void setAffi_state(String affi_state) {
		this.affi_state = affi_state;
	}

	public Integer getAffi_no() {
		return affi_no;
	}

	public void setAffi_no(Integer affi_no) {
		this.affi_no = affi_no; // this ���i���g
	}

	
	public String getBus_no() {
		return bus_no;
	}

	public void setBus_no(String bus_no) {
		this.bus_no = bus_no;
	}

	public String getRest_name() {
		return rest_name;
	}

	public void setRest_name(String rest_name) {
		this.rest_name = rest_name;
	}

	public String getRest_addr() {
		return rest_addr;
	}

	public void setRest_addr(String rest_addr) {
		this.rest_addr = rest_addr;
	}

	public String getRest_tel() {
		return rest_tel;
	}

	public void setRest_tel(String rest_tel) {
		this.rest_tel = rest_tel;
	}

	public String getRest_mail() {
		return rest_mail;
	}

	public byte[] getRest_photo() {
		return rest_photo;
	}

	public void setRest_photo(byte[] rest_photo) {
		this.rest_photo = rest_photo;
	}

	public void setRest_mail(String rest_mail) {
		this.rest_mail = rest_mail;
	}

	public String getRest_web() {
		return rest_web;
	}

	public void setRest_web(String rest_web) {
		this.rest_web = rest_web;
	}

	public String getRest_intro() {
		return rest_intro;
	}

	public void setRest_intro(String rest_intro) {
		this.rest_intro = rest_intro;
	}

	public String getRest_mobil() {
		return rest_mobil;
	}

	public void setRest_mobil(String rest_mobil) {
		this.rest_mobil = rest_mobil;
	}

}
