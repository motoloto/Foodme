package com.mem.model;

import java.io.Serializable;
import java.sql.Date;

public class MemVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer mem_no;
	private String  mem_account;
	private String  mem_pwd;
	private String  mem_name;
	private String  mem_mail;
	private String  mem_phone;
	private String  mem_adrs;
	private Date    mem_birthdate;
	private String  mem_sex;
	private String  mem_nickname;
	private Integer  mem_illtms;
	private byte[]   mem_pic;
	
	public Integer getMem_no() {
		return mem_no;
	}
	public void setMem_no(Integer mem_no) {
		this.mem_no = mem_no;
	}
	public String getMem_account() {
		return mem_account;
	}
	public void setMem_account(String mem_account) {
		this.mem_account = mem_account;
	}
	public String getMem_pwd() {
		return mem_pwd;
	}
	public void setMem_pwd(String mem_pwd) {
		this.mem_pwd = mem_pwd;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_mail() {
		return mem_mail;
	}
	public void setMem_mail(String mem_mail) {
		this.mem_mail = mem_mail;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_adrs() {
		return mem_adrs;
	}
	public void setMem_adrs(String mem_adrs) {
		this.mem_adrs = mem_adrs;
	}
	public Date getMem_birthdate() {
		return mem_birthdate;
	}
	public void setMem_birthdate(Date mem_birthdate) {
		this.mem_birthdate = mem_birthdate;
	}
	public String getMem_sex() {
		return mem_sex;
	}
	public void setMem_sex(String mem_sex) {
		this.mem_sex = mem_sex;
	}
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public Integer getMem_illtms() {
		return mem_illtms;
	}
	public void setMem_illtms(Integer mem_illtms) {
		this.mem_illtms = mem_illtms;
	}
	
	
	public byte[] getMem_pic() {
		return mem_pic;
	}
	public void setMem_pic(byte[] mem_pic) {
		this.mem_pic = mem_pic;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
}
