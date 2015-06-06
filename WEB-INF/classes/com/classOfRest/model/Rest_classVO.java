package com.classOfRest.model;

import java.io.Serializable;

public class Rest_classVO implements Serializable{
    private Integer rest_no;
    private Integer class_no;
    
	public Integer getRest_no() {
		return rest_no;
	}
	public void setRest_no(Integer rest_no) {
		this.rest_no = rest_no;
	}
	public Integer getClass_no() {
		return class_no;
	}
	public void setClass_no(Integer class_no) {
		this.class_no = class_no;
	}
   
}
