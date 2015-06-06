package com.restClass.model;

import java.io.Serializable;

public class ClassVO implements Serializable{
    private Integer class_no;
    private String class_name;
    
	public Integer getClass_no() {
		return class_no;
	}
	public void setClass_no(Integer class_no) {
		this.class_no = class_no;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
    
    
}
