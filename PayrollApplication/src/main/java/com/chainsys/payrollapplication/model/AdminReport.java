package com.chainsys.payrollapplication.model;

public class AdminReport {
	int empCode;
	public String name;
	public String projectTitle;
	public String projectFeatures;
	public int timeDurations;
	public String status;
	String reason;
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getProjectFeatures() {
		return projectFeatures;
	}

	public void setProjectFeatures(String projectFeatures) {
		this.projectFeatures = projectFeatures;
	}

	public int getTimeDurations() {
		return timeDurations;
	}

	public void setTimeDurations(int timeDurations) {
		this.timeDurations = timeDurations;
	}

	public AdminReport() {

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



}
