package com.chainsys.payrollapplication.model;

public class LeaveReport {

	 int emp_code;
	 String name;
	 String fromdate;
	 public int getEmp_code() {
		return emp_code;
	}
	public LeaveReport() {
		super();
	}
	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LeaveReport(int emp_code, String name, String fromdate, String toDate, String leaveType, String leaveCount,
			String status) {
		super();
		this.emp_code = emp_code;
		this.name = name;
		this.fromdate = fromdate;
		this.toDate = toDate;
		this.leaveType = leaveType;
		this.leaveCount = leaveCount;
		this.status = status;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getLeaveCount() {
		return leaveCount;
	}
	public void setLeaveCount(String leaveCount) {
		this.leaveCount = leaveCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	String toDate;
	 String leaveType;
	 String leaveCount;
	 String status;
	
	
}
