package com.msg.model;
import java.sql.Date;

public class MsgVO implements java.io.Serializable{
	private Integer	msg_no;
	private Integer rest_no;
	private String msg_name;
	private String msg_cont;
	public Integer getMsg_no() {
		return msg_no;
	}
	public void setMsg_no(Integer mess_no) {
		this.msg_no = mess_no;
	}
	public Integer getRest_no() {
		return rest_no;
	}
	public void setRest_no(Integer rest_no) {
		this.rest_no = rest_no;
	}
	public String getMsg_name() {
		return msg_name;
	}
	public void setMsg_name(String mess_name) {
		this.msg_name = mess_name;
	}
	public String getMsg_cont() {
		return msg_cont;
	}
	public void setMsg_cont(String mess_cont) {
		this.msg_cont = mess_cont;
	}
	
}
