package com.rest.model;

import java.util.ArrayList;
import java.util.List;

public class ReservRecord {
	Integer reservMax; //最大可定位數
	Integer rest_no;
	String rest_name;
	String rest_addr;
	String rest_tel;
	String rest_opentime;
	Integer rest_waitmin;
	
	ArrayList<PerDay> recordOfWeek;  //一周的可訂位數
	
	
	public Integer getReservMax() {
		return reservMax;
	}


	public void setReservMax(Integer reservMax) {
		this.reservMax = reservMax;
	}


	public Integer getRest_no() {
		return rest_no;
	}


	public void setRest_no(Integer rest_no) {
		this.rest_no = rest_no;
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


	public String getRest_opentime() {
		return rest_opentime;
	}


	public void setRest_opentime(String rest_opentime) {
		this.rest_opentime = rest_opentime;
	}


	public Integer getRest_waitmin() {
		return rest_waitmin;
	}


	public void setRest_waitmin(Integer rest_waitmin) {
		this.rest_waitmin = rest_waitmin;
	}


	public Integer getMax() {
		return reservMax;
	}


	public void setMax(Integer max) {
		this.reservMax = max;
	}


	public ArrayList<PerDay> getRecordOfWeek() {
		return recordOfWeek;
	}


	public void setRecordOfWeek(ArrayList<PerDay> recordOfWeek) {
		this.recordOfWeek = recordOfWeek;
	}


}
