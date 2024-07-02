package com.chainsys.payrollapplication.model;

public class PermissionCount {

    public int empCode;
    public String name;
    public String date;
    public String startTime;
    public String endTime;
    public String status;
    public String permissionCount;

    public PermissionCount() {
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

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}



	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPermissionCount() {
		return permissionCount;
	}

	public void setPermissionCount(String permissionCount) {
		this.permissionCount = permissionCount;
	}

}

