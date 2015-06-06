package com.rest.model;

import java.util.ArrayList;
import java.util.List;

public class PerDay {
	String day;  //星期幾
	ArrayList<PerTime>  recordOfDay;   //一天的可訂位數

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}


	public ArrayList<PerTime> getRecordOfDay() {
		return recordOfDay;
	}

	public void setRecordOfDay(ArrayList<PerTime> recordOfDay) {
		this.recordOfDay = recordOfDay;
	}
}
