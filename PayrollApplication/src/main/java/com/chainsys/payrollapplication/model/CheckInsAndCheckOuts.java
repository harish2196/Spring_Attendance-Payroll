package com.chainsys.payrollapplication.model;

public class CheckInsAndCheckOuts {

	int empCode;
	String name;
	String checkIn;
	String checkOut;
	
	
	
	public CheckInsAndCheckOuts(int empCode, String name) {
		
		this.empCode = empCode;
		this.name = name;
	}
	public int getEmpCode() {
		return empCode;
	}
	public void setEmpCode(int empCode) {
		this.empCode = empCode;
	}
	public String getName() {
		return name;
	}
	public CheckInsAndCheckOuts() {
		super();
	}
	@Override
	public String toString() {
		return "CheckInsAndCheckOuts [empCode=" + empCode + ", name=" + name + ", checkIn=" + checkIn + ", checkOut="
				+ checkOut + "]";
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	
}
