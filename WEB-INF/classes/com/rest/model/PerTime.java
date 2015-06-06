package com.rest.model;

public class PerTime {
	private String time;
	private int residual;
	public PerTime(String time, int residual) {
		super();
		this.time = time;
		this.residual = residual;
	}

	public PerTime() {

	}


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getResidual() {
		return residual;
	}

	public void setResidual(int residual) {
		this.residual = residual;
	}
}