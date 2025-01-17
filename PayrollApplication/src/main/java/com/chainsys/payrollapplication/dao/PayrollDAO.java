package com.chainsys.payrollapplication.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.chainsys.payrollapplication.model.AdminReport;
import com.chainsys.payrollapplication.model.CheckInsAndCheckOuts;
import com.chainsys.payrollapplication.model.EmployeePayScale;
import com.chainsys.payrollapplication.model.Employees;
import com.chainsys.payrollapplication.model.LeaveReport;
import com.chainsys.payrollapplication.model.PayrollList;
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
	public void insertAdminReport(AdminReport adminReport);
	public List<Employees> viewEmployeeDetails();
	public boolean isUserExist(String useremail, String usermobile); 
	public List<CheckInsAndCheckOuts> viewCheckInsAndOuts();
	public void updateEmployeeDetails(Employees employee);
	public void deleteEmployeeById(int id);
	public List<AdminReport> getComments(int empCode);
	public List<PermissionCount> getPermissionInfo();
	public List<LeaveReport> getAllLeaveReports();
	public boolean updatePermissionStatus(int empcode, String status); 
	//	public int remainRejectLeaveDays(int id);
	public boolean insertTotalLeaveDays(int empCode, int totalLeaveDays);
	public int getTotalLeaveDays(int id, String toDate, String fromDate);
	public boolean updateLeaveStatus(int empCode, String status);
	public List<CheckInsAndCheckOuts> getEmployeeCheckInOut(int empCode);
	public int getTotalWorkingHours(int empCode); 
	public String getEmployeeName(int empCode);
	public String getEmployeeEmail(int empCode);
	public int countPermissionsPayroll(int empCode);
	public int countSickLeavePayroll(int empCode);
	public int countCasualLeavePayroll(int empCode);
	public int getTotalCheckinCount(int empCode);
	public int getEmployeeSalary(int empCode);			
	public int insertOrUpdateLeavePermission(PayrollList payrollList);
	public List<EmployeePayScale> getAllEmployeePayScales();
	public void payrollPays(EmployeePayScale employeePayScale, int empCode);
	public List<PermissionCount> getPermissionStatus(int EmpCode);
	public List<LeaveReport> getAllLeaveStatus(int empCode);
	public int getEmployeePayscaleSalary(int empCode); 
	public void salaryCredited(int empCode); 
	public List<Employees> getEmployeeDeatils(int empCode);
	public List<AdminReport> getReport(int empCode);
	public List<EmployeePayScale> searchEmployeePayScales(int empCode);
	public List<PermissionCount> searchPermission(int empCode);
	public List<LeaveReport> searchLeaveReports(int empCode);
	public int insertOrUpdateLeavePermission(PayrollList payrollList,int empCode); 
	public void salaryPending(int empCode);
	public boolean isSalaryCredited(int empCode);
	public void permissionCountDeleteByDays(int empCode);
	public void leaveCountDeleteByDays(int empCode);
	public void checkInsOutsDeleteByDays(int empCode);
	public void EmpPayscaleDeleteByDays(int empCode);
	public List<Employees> getExistingEmployeeCodes();
	public int getTimeDurations(int empCode);
	public boolean updateTaskList(int empCode, String status);
	public List<AdminReport> getAdminTaskList();
	public void updateTaskDuration(int empCode, int timeDuration); 
	public List<CheckInsAndCheckOuts> getEmployeeCodes(String date);
	public void reasonForRejection(int empCode, String reason);

}
