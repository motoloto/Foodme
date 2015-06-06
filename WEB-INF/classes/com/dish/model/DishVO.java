package com.dish.model;
import java.sql.Date;

public class DishVO implements java.io.Serializable{
	private Integer dish_no;
	private Integer rest_no;
	private String dish_name;
	private byte[] dish_pic;
	private String dish_state;
	private String dish_cont;
	private Integer dish_price;
	//clob 先skip
	
	
	public Integer getDish_no() {
		return dish_no;
	}
	public String getDish_cont() {
		return dish_cont;
	}
	public void setDish_cont(String dish_cont) {
		this.dish_cont = dish_cont;
	}
	public void setDish_no(Integer dish_no) {
		this.dish_no = dish_no;
	}
	public Integer getRest_no() {
		return rest_no;
	}
	public void setRest_no(Integer rest_no) {
		this.rest_no = rest_no;
	}
	public String getDish_name() {
		return dish_name;
	}
	public void setDish_name(String dish_name) {
		this.dish_name = dish_name;
	}
	public byte[] getDish_pic() {
		return dish_pic;
	}
	public void setDish_pic(byte[] dish_pic) {
		this.dish_pic = dish_pic;
	}
	public String getDish_state() {
		return dish_state;
	}
	public void setDish_state(String dish_state) {
		this.dish_state = dish_state;
	}
	public Integer getDish_price() {
		return dish_price;
	}
	public void setDish_price(Integer dish_price) {
		this.dish_price = dish_price;
	}		
	
}
