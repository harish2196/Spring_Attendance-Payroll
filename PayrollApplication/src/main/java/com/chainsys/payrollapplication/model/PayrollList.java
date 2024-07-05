package com.chainsys.payrollapplication.model;

public class PayrollList {

	int empCode;
	String empName;
	String empEmail;
	int permissionCount;
	int sickLeaveDays;
	int casualLeaveDays;
	int totalCheckinCount;
	int salary;
	int workingHours;
	
	public int getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(int workingHours) {
		this.workingHours = workingHours;
	}

	public PayrollList() {
    }

	public int getEmpCode() {
		return empCode;
	}

	public void setEmpCode(int empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public int getPermissionCount() {
		return permissionCount;
	}

	public void setPermissionCount(int permissionCount) {
		this.permissionCount = permissionCount;
	}

	public int getSickLeaveDays() {
		return sickLeaveDays;
	}

	public void setSickLeaveDays(int sickLeaveDays) {
		this.sickLeaveDays = sickLeaveDays;
	}

	public int getCasualLeaveDays() {
		return casualLeaveDays;
	}

	public void setCasualLeaveDays(int casualLeaveDays) {
		this.casualLeaveDays = casualLeaveDays;
	}

	public int getTotalCheckinCount() {
		return totalCheckinCount;
	}

	public void setTotalCheckinCount(int totalCheckinCount) {
		this.totalCheckinCount = totalCheckinCount;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
}
