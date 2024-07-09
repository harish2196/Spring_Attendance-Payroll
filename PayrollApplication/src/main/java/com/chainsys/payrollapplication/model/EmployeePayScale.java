package com.chainsys.payrollapplication.model;

public class EmployeePayScale {

	  public int id;
	    public int empCode;
	    public String username;
	    public String userEmail;
	    public String payrollPermission;
		public int sickLeaveDays;
	    public int casualLeaveDays;
	    public int workingDays;
	    public double getPermissionPayscale() {
			return permissionPayscale;
		}

		public void setPermissionPayscale(double permissionPayscale) {
			this.permissionPayscale = permissionPayscale;
		}

		public double getSickLeavePayscale() {
			return sickLeavePayscale;
		}

		public void setSickLeavePayscale(double sickLeavePayscale) {
			this.sickLeavePayscale = sickLeavePayscale;
		}

		public double getCasualLeavePayscale() {
			return casualLeavePayscale;
		}

		public void setCasualLeavePayscale(double casualLeavePayscale) {
			this.casualLeavePayscale = casualLeavePayscale;
		}



		public double permissionPayscale;
	    public double sickLeavePayscale;
	    public double casualLeavePayscale;
	    
	    public String getSalaryStatus() {
			return salaryStatus;
		}



		public int workingHours;
	    public String salaryStatus;
	    public void setSalaryStatus(String salaryStatus) {
			this.salaryStatus = salaryStatus;
		}



		public int salary;
		public int pf;
	    public int netPay;
	    public double grossPay;
	   
	    
	    public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getEmpCode() {
			return empCode;
		}

		public void setEmpCode(int empCode) {
			this.empCode = empCode;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUserEmail() {
			return userEmail;
		}

		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}

		public String getPayrollPermission() {
			return payrollPermission;
		}

		public void setPayrollPermission(String payrollPermission) {
			this.payrollPermission = payrollPermission;
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

		public int getWorkingDays() {
			return workingDays;
		}

		public void setWorkingDays(int workingDays) {
			this.workingDays = workingDays;
		}

		public int getWorkingHours() {
			return workingHours;
		}

		public void setWorkingHours(int workingHours) {
			this.workingHours = workingHours;
		}

		public int getSalary() {
			return salary;
		}

		public void setSalary(int salary) {
			this.salary = salary;
		}

		public double getGrossPay() {
			return grossPay;
		}

		public void setGrossPay(double grossPay) {
			this.grossPay = grossPay;
		}

		public int getPf() {
			return pf;
		}

		public void setPf(int pf) {
			this.pf = pf;
		}

		public int getNetPay() {
			return netPay;
		}

		public void setNetPay(int netPay) {
			this.netPay = netPay;
		}

	

	    public EmployeePayScale() {
	    	
	    }
	
}
