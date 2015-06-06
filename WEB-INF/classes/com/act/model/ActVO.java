package com.act.model;
import java.sql.Date;

public class ActVO implements java.io.Serializable{
	private Integer	act_no; 
	private Integer rest_no;
	private String act_name;
	private String act_cont;
	private String act_time;
	private String act_state;
	private byte[] act_photo;
		
	public byte[] getAct_photo() {
		return act_photo;
	}
	public void setAct_photo(byte[] act_photo) {
		this.act_photo = act_photo;
	}
	public Integer getAct_no() {
		return act_no;
	}
	public void setAct_no(Integer act_no) {
		this.act_no = act_no;
	}
	public Integer getRest_no() {
		return rest_no;
	}
	public void setRest_no(Integer rest_no) {
		this.rest_no = rest_no;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getAct_cont() {
		return act_cont;
	}
	public void setAct_cont(String act_cont) {
		this.act_cont = act_cont;
	}
	public String getAct_time() {
		return act_time;
	}
	public void setAct_time(String act_time) {
		this.act_time = act_time;
	}
	public String getAct_state() {
		return act_state;
	}
	public void setAct_state(String act_state) {
		this.act_state = act_state;
	}
}
