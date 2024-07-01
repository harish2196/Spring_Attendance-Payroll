package com.chainsys.payrollapplication.model;

public class AdminReport {
	int emp_code;
	private String name;
	private String text;

	public AdminReport() {

	}

	public AdminReport(int emp_code, String name, String text) {
		this.emp_code = emp_code;
		this.name = name;
		this.text = text;
	}


	public int getEmp_code() {
		return emp_code;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;

	}
}
