package com.chainsys.payrollapplication.model;

import org.springframework.stereotype.Component;


@Component
public class EmployeeDetails {

    public int emp_code;
    public String username;
    public String designation;
    public String useremail;
    public String userpassword;
    public String usermobile;
    public byte[] imageData;
    public int salary;

    public int getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getUsermobile() {
		return usermobile;
	}

	public void setUsermobile(String usermobile) {
		this.usermobile = usermobile;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	// Default constructor (required by Spring)
    public EmployeeDetails() {
    }

    // Constructor with necessary fields
    public EmployeeDetails(int emp_code, String username, String designation, String useremail, String userpassword, String usermobile, byte[] imageData, int salary) {
        this.emp_code = emp_code;
        this.username = username;
        this.designation = designation;
        this.useremail = useremail;
        this.userpassword = userpassword;
        this.usermobile = usermobile;
        this.imageData = imageData;
        this.salary = salary;
    }

    // Getters and setters
    // toString() method
}


