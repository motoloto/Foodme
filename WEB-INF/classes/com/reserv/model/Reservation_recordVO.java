package com.reserv.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Reservation_recordVO implements Serializable {
    private Integer rec_no;
    private Integer mem_no;
    private String rest_name;
    private Integer rest_no;
    private Integer count;
    private String reservation_day;
    private Integer period;
    private String odr_seqnum;
    private String seating;
    
	public String getRest_name() {
		return rest_name;
	}
	public void setRest_name(String rest_name) {
		this.rest_name = rest_name;
	}
	public Integer getRec_no() {
		return rec_no;
	}
	public void setRec_no(Integer rec_no) {
		this.rec_no = rec_no;
	}
	public Integer getMem_no() {
		return mem_no;
	}
	public void setMem_no(Integer mem_no) {
		this.mem_no = mem_no;
	}
	public Integer getRest_no() {
		return rest_no;
	}
	public void setRest_no(Integer rest_no) {
		this.rest_no = rest_no;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getReservation_day() {
		return reservation_day;
	}
	public void setReservation_day(String reservation_day) {
		this.reservation_day = reservation_day;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getOdr_seqnum() {
		return odr_seqnum;
	}
	public void setOdr_seqnum(String odr_seqnum) {
		this.odr_seqnum = odr_seqnum;
	}
	public String getSeating() {
		return seating;
	}
	public void setSeating(String seating) {
		this.seating = seating;
	}
		
    
}
