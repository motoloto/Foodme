package com.cop.model;

import java.io.Serializable;
import java.sql.Date;

public class CopVO implements Serializable {
	
	private Integer Cop_no;
	private Integer Rest_no;
	private String  Cop_name;
	private String  Cop_content;
	private Integer Cop_orlprice;
	private Integer Cop_price;
	private Date    Cop_dl;
	private String  Cop_state;
	private Date    Cop_date;
	private Integer Cop_circu;
	private Integer Cop_selamt;

	public Integer getCop_no() {
		return Cop_no;
	}
	public void setCop_no(Integer cop_no) {
		Cop_no = cop_no;
	}
	public Integer getRest_no() {
		return Rest_no;
	}
	public void setRest_no(Integer rest_no) {
		Rest_no = rest_no;
	}
	public String getCop_name() {
		return Cop_name;
	}
	public void setCop_name(String cop_name) {
		Cop_name = cop_name;
	}
	public String getCop_content() {
		return Cop_content;
	}
	public void setCop_content(String cop_content) {
		Cop_content = cop_content;
	}
	public Integer getCop_orlprice() {
		return Cop_orlprice;
	}
	public void setCop_orlprice(Integer cop_orlprice) {
		Cop_orlprice = cop_orlprice;
	}
	public Integer getCop_price() {
		return Cop_price;
	}
	public void setCop_price(Integer cop_price) {
		Cop_price = cop_price;
	}
	public Date getCop_dl() {
		return Cop_dl;
	}
	public void setCop_dl(Date cop_dl) {
		Cop_dl = cop_dl;
	}
	public String getCop_state() {
		return Cop_state;
	}
	public void setCop_state(String cop_state) {
		Cop_state = cop_state;
	}
	public Date getCop_date() {
		return Cop_date;
	}
	public void setCop_date(Date cop_date) {
		Cop_date = cop_date;
	}
	public Integer getCop_circu() {
		return Cop_circu;
	}
	public void setCop_circu(Integer cop_circu) {
		Cop_circu = cop_circu;
	}
	public Integer getCop_selamt() {
		return Cop_selamt;
	}
	public void setCop_selamt(Integer cop_selamt) {
		Cop_selamt = cop_selamt;
	}
	
	
	
}
