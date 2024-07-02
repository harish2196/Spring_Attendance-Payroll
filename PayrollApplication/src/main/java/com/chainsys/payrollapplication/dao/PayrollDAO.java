package com.chainsys.payrollapplication.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.chainsys.payrollapplication.model.AdminReport;
import com.chainsys.payrollapplication.model.Employees;
import com.chainsys.payrollapplication.model.LeaveReport;
import com.chainsys.payrollapplication.model.PermissionCount;

@Repository
public interface PayrollDAO {

	
	public void insertEmployee(Employees employees);
	  public  boolean login(String username, String password);
	  public int getEmployeeCode(String username);
	public void insertCheckInTime(int empCode, String name);
	 public void insertCheckOutTime(int empCode);
	 public void insertPermission(PermissionCount permissionCount,int empCode);
	 public void insertLeaveReport(LeaveReport leaveReport, int empCode);
	 public void insertAdminReport(AdminReport adminReport, int empCode);
	 public Employees selectEmployee();
	 public boolean isUserExist(String useremail, String usermobile); 
}
