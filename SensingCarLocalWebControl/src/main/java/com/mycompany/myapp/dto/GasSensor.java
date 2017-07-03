package com.mycompany.myapp.dto;

import java.util.Date;

public class GasSensor {
	private int gno;
	private double gvalue;
	private Date gdate;
	public int getGno() {
		return gno;
	}
	public void setGno(int gno) {
		this.gno = gno;
	}
	public double getGvalue() {
		return gvalue;
	}
	public void setGvalue(double gvlaue) {
		this.gvalue = gvlaue;
	}
	public Date getGdate() {
		return gdate;
	}
	public void setGdate(Date gdate) {
		this.gdate = gdate;
	}
	
	

}
