package com.chainsys.payrollapplication.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.payrollapplication.mapper.AdminReportMapper;
import com.chainsys.payrollapplication.mapper.CheckInOutHandler;
import com.chainsys.payrollapplication.mapper.EmployeePayscaleMapper;
import com.chainsys.payrollapplication.mapper.GetEmpCodeMapper;
import com.chainsys.payrollapplication.mapper.LeaveInfoMapper;
import com.chainsys.payrollapplication.mapper.LoginMapper;
import com.chainsys.payrollapplication.mapper.PayrollMapper;
import com.chainsys.payrollapplication.mapper.PermissionMapper;
import com.chainsys.payrollapplication.model.AdminReport;
import com.chainsys.payrollapplication.model.CheckInsAndCheckOuts;
import com.chainsys.payrollapplication.model.EmployeePayScale;
import com.chainsys.payrollapplication.model.Employees;
import com.chainsys.payrollapplication.model.LeaveReport;
import com.chainsys.payrollapplication.model.PayrollList;
import com.chainsys.payrollapplication.model.PermissionCount;

@Repository
public class PayrollDAOImpl implements PayrollDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;


	public void insertEmployee(Employees employees) {
		String insertQuery = "INSERT INTO Employee_details (emp_code, username, designation, useremail, userpassword, usermobile, image, salary) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = { employees.getEmpCode(), employees.getUserName(), 
				employees.getDesignation(), employees.getUserEmail(), 
				employees.getUserPassword(), employees.getUserMobile(), 
				employees.getImageData(), employees.getSalary() };

		int rowsAffected = jdbcTemplate.update(insertQuery, params);
		System.out.println("Rows Affected: " + rowsAffected);
	}

	public  boolean login(String username, String password) {
		String sql = "SELECT username,userpassword FROM Employee_details WHERE username = ? AND userpassword = ?";
		Employees count = jdbcTemplate.queryForObject(sql, new LoginMapper(), username, password);
		return true;
	}

	public int getEmployeeCode(String username) {
		String sql = "SELECT emp_code FROM Employee_details WHERE username = ?";	
		return jdbcTemplate.queryForObject(sql,new GetEmpCodeMapper(), username);

	}


	public void insertCheckInTime(int empCode, String name) {
		try {
			String insertCheckInQuery = "INSERT INTO checkins_checkouts (emp_code, name, checkin_time) VALUES (?, ?, NOW())";
			jdbcTemplate.update(insertCheckInQuery, empCode, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertCheckOutTime(int empCode) {
		try {
			String insertCheckOutQuery = "UPDATE checkins_checkouts SET checkout_time = NOW() WHERE emp_code = ? AND checkout_time IS NULL";
			jdbcTemplate.update(insertCheckOutQuery, empCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  

	public void insertPermission(PermissionCount permissionCount, int empCode) {
		String checkPermissionQuery = "SELECT COUNT(*) FROM permission_count WHERE emp_code = ?";
		int count = jdbcTemplate.queryForObject(checkPermissionQuery, Integer.class, empCode);

		if (count > 0) {
			String updatePermissionQuery = "UPDATE permission_count SET date = ?, start_time = ?, end_time = ?, status = 'waiting', permission = permission,message_text=? WHERE emp_code = ?";
			jdbcTemplate.update(updatePermissionQuery, permissionCount.getDate(), permissionCount.getStartTime(),
					permissionCount.getEndTime(),permissionCount.getInfoText(), empCode);
			System.out.println("Permission count updated successfully.");
		} else {
			String insertPermissionQuery = "INSERT INTO permission_count (emp_code, name, date, start_time, end_time, permission,message_text) VALUES (?, ?, ?, ?, ?, 0,?)";
			Object[] params = { empCode, permissionCount.getName(),
					permissionCount.getDate(), permissionCount.getStartTime(),
					permissionCount.getEndTime(),permissionCount.getInfoText()};
			int rowsAffected = jdbcTemplate.update(insertPermissionQuery, params);
			System.out.println("Rows Affected: " + rowsAffected);
		}
	}

	public void insertLeaveReport(LeaveReport leaveReport, int empCode) {
		String sql = "INSERT INTO leave_report (emp_code, name, from_date, to_date, leave_type,message_text)  VALUES (?, ?, ?, ?, ?, ?)";
		Object[] params = { empCode, leaveReport.getName(), leaveReport.getFromdate(),
				leaveReport.getTodate(), leaveReport.getLeaveType(),leaveReport.getReason() };
		int rowsAffected = jdbcTemplate.update(sql, params);      
	}

	public void insertAdminReport(AdminReport adminReport) {
		String sql = "INSERT INTO admin_report (emp_code, name,project_title,project_features,time_duration) VALUES (?, ?, ?, ?, ?)";
		Object[] params = {adminReport.getEmpCode(),adminReport.getName(),adminReport.getProjectTitle(),adminReport.getProjectFeatures(),adminReport.getTimeDurations() };
		int rowsAffected = jdbcTemplate.update(sql, params);
	
	}

	public List<Employees> viewEmployeeDetails() {
		String getAllQuery = "SELECT emp_code,username,designation,useremail,userpassword,usermobile,image,salary FROM Employee_details";
		return jdbcTemplate.query(getAllQuery, new PayrollMapper());
	}

	public List<CheckInsAndCheckOuts> viewCheckInsAndOuts() {
		String getAllQuery = "SELECT emp_code,name,checkin_time,checkout_time FROM checkins_checkouts";
		return jdbcTemplate.query(getAllQuery, new CheckInOutHandler());
	}

	public List<AdminReport> getComments(int empCode) {
		String getAllQuery = "SELECT emp_code,name,project_title,project_features,time_duration,status,reason FROM admin_report WHERE emp_code=?";
		return jdbcTemplate.query(getAllQuery, new AdminReportMapper(),empCode);
	}
	
	public List<AdminReport> getAdminTaskList() {
		String getAllQuery = "SELECT emp_code,name,project_title,project_features,time_duration,status,reason FROM admin_report";
		return jdbcTemplate.query(getAllQuery, new AdminReportMapper());
	}


	public boolean isUserExist(String useremail, String usermobile) {
		String sql = "SELECT count(*) FROM Employee_details WHERE useremail = ? AND usermobile = ?";
		try {
			int count = jdbcTemplate.queryForObject(sql, Integer.class, useremail, usermobile);
			return count > 0;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	public void updateEmployeeDetails(Employees employee) {
		String updateQuery = "UPDATE Employee_details SET username=?, designation=?, useremail=?, usermobile=?, salary=? WHERE emp_code=?";
		Object[] params = {employee.getUserName(), employee.getDesignation(),
				employee.getUserEmail(), employee.getUserMobile(),
				employee.getSalary(), employee.getEmpCode()};	        
		int rowsAffected = jdbcTemplate.update(updateQuery, params);
		System.out.println("Rows Affected by update operation: " + rowsAffected);
	}

	public void deleteEmployeeById(int id) {
		String deleteQuery = "DELETE FROM Employee_details WHERE emp_code=?";
		int rowsAffected = jdbcTemplate.update(deleteQuery, id);
	} 

	public List<PermissionCount> getPermissionInfo(){
		String getAllQuery = "SELECT emp_code, name, date, start_time, end_time, status, permission,message_text FROM permission_count";
		return jdbcTemplate.query(getAllQuery, new PermissionMapper());
	}

	public List<LeaveReport> getAllLeaveReports() {
		String getAllQuery = "SELECT id,emp_code, name, from_date, to_date, leave_type, leave_Count,message_text, status FROM Leave_report";
		return jdbcTemplate.query(getAllQuery, new LeaveInfoMapper());
	}

	public boolean updatePermissionStatus(int empCode, String status) {
		boolean isSuccess = false;

		String updateQuery = "UPDATE permission_count SET status = ? WHERE emp_code = ?";
		int rowCount = jdbcTemplate.update(updateQuery, status, empCode);

		if (rowCount > 0) {
			isSuccess = true;

			if ("accepted".equalsIgnoreCase(status)) {
				String updatePermission = "UPDATE permission_count SET permission = permission + 1 WHERE emp_code = ?";
				jdbcTemplate.update(updatePermission, empCode);
			} else if ("rejected".equalsIgnoreCase(status)) {
				String updatePermission = "UPDATE permission_count SET permission = permission WHERE emp_code = ?";
				jdbcTemplate.update(updatePermission, empCode);
			}
		}

		return isSuccess;
	}

	public int getTotalLeaveDays(int id, String toDate, String fromDate) {
	    String query = "SELECT DATEDIFF(?, ?) AS total_days FROM Leave_report WHERE id = ?";	    
	    try {
	        Integer totalLeaveDays = jdbcTemplate.queryForObject(query, Integer.class, toDate, fromDate, id);
	        if (totalLeaveDays != null) {
	            return totalLeaveDays;
	        } else {
	            return 0; 
	        }
	    } catch (DataAccessException e) {	       
	        return 0;
	    }
	}


	public boolean insertTotalLeaveDays(int id, int totalLeaveDays) {
		String updateQuery = "UPDATE Leave_report SET leave_Count = ? WHERE id = ?";
		int rowCount = jdbcTemplate.update(updateQuery, totalLeaveDays, id);

		if (rowCount > 0) {
			return true;
		} else {	       
			return false; 
		}
	}

	public boolean updateLeaveStatus(int id, String status) {
		String query = "UPDATE Leave_report SET status = ? WHERE id = ?";
		int rowCount = jdbcTemplate.update(query, status, id);
		if (rowCount > 0) {
			return true;
		} else {	           
			return false;
		}
	}

	public List<CheckInsAndCheckOuts> getEmployeeCheckInOut(int empCode) {
		String selectQuery = "SELECT emp_code, name, checkin_time, checkout_time FROM checkins_checkouts WHERE emp_code=?";
		List<CheckInsAndCheckOuts> employees = jdbcTemplate.query(selectQuery,new CheckInOutHandler(),empCode);
		return employees;
	}

	public int getTotalWorkingHours(int empCode) {
		try {
			String sumQuery ="SELECT SUM(TIMESTAMPDIFF(MINUTE, checkin_time, checkout_time)) / 60 AS total_minutes FROM checkins_checkouts WHERE emp_code = ?";
			int count = jdbcTemplate.queryForObject(sumQuery, Integer.class, empCode);
			return count;			
		}catch(Exception e) {
			return 0; 
		}
	}

	public String getEmployeeName(int empCode) {
		String getName = "SELECT username FROM Employee_details WHERE emp_code = ?";
		return jdbcTemplate.queryForObject(getName, String.class, empCode);
	}

	public String getEmployeeEmail(int empCode) {
		String getEmail = "SELECT useremail FROM Employee_details WHERE emp_code = ?";
		return jdbcTemplate.queryForObject(getEmail, String.class, empCode);
	}

	public int countPermissionsPayroll(int empCode) {
        String getPermission = "SELECT permission FROM permission_count WHERE emp_code = ? AND status = 'Accepted'";
        try {
			return jdbcTemplate.queryForObject(getPermission, Integer.class, empCode);		  
		}catch(Exception e) {
			return 0;
		}
	}

	public int countSickLeavePayroll(int empCode) {
		 String sickLeave = "SELECT SUM(DATEDIFF(to_date, from_date)) AS sick_leave_days FROM Leave_report WHERE emp_code = ? AND leave_type = 'sick' AND status = 'Accepted'";
		 try {
			return jdbcTemplate.queryForObject(sickLeave, Integer.class, empCode);	
		}catch(Exception e) {
			return 0;
		}
	}

	public int countCasualLeavePayroll(int empCode) {
		String casualLeave = "SELECT SUM(DATEDIFF(to_date, from_date)) AS casual_leave_days FROM Leave_report WHERE emp_code = ? AND leave_type = 'casual' AND status = 'Accepted' ";
		try {
			return jdbcTemplate.queryForObject(casualLeave, Integer.class, empCode);
		}catch(Exception e) {
			return 0;
		}
	}

	public int getTotalCheckinCount(int empCode) {
		String checkInCount = "SELECT COUNT(checkin_time) AS checkin_count FROM checkins_checkouts WHERE emp_code = ?";
		return jdbcTemplate.queryForObject(checkInCount, Integer.class, empCode);		  		  	  		
	}

	public int getEmployeeSalary(int empCode) {
		String getsalary = "SELECT salary FROM Employee_details WHERE emp_code = ?";
		try {
			return jdbcTemplate.queryForObject(getsalary, Integer.class, empCode);
		}catch(Exception e) {
			return -1;
		}
	}

	public int getEmployeePayscaleSalary(int empCode) {
		String getsalary = "SELECT salary FROM employee_payscale WHERE emp_code = ?";
		try {
			return jdbcTemplate.queryForObject(getsalary, Integer.class, empCode);
		}catch(Exception e) {
			return -1;
		}
	}


	public int insertOrUpdateLeavePermission(PayrollList payrollList) {
		int affectedRows = 0;

		try {
			String checkQuery = "SELECT COUNT(*) FROM employee_payscale WHERE emp_code = ?";
			int count = jdbcTemplate.queryForObject(checkQuery, Integer.class, payrollList.getEmpCode());

			if (count > 0) {
				String updateQuery = "UPDATE employee_payscale SET username = ?, useremail = ?, payroll_permission = ?, sick_leaveDays = ?, casual_leaveDays = ?, working_days = ?,working_hours= ?, salary = ? WHERE emp_code = ?";
				affectedRows = jdbcTemplate.update(updateQuery, payrollList.getEmpName(), payrollList.getEmpEmail(),
						payrollList.getPermissionCount(), payrollList.getSickLeaveDays(),
						payrollList.getCasualLeaveDays(), payrollList.getTotalCheckinCount(),payrollList.getWorkingHours(),
						payrollList.getSalary(), payrollList.getEmpCode());

				if (affectedRows > 0) {
					System.out.println("Data updated successfully.");
				} else {
					System.out.println("Data update failed.");
				}
			} else {
				String insertQuery = "INSERT INTO employee_payscale " + "(emp_code, username, useremail, payroll_permission, " + "sick_leaveDays, casual_leaveDays, working_days, " +"working_hours, salary, salary_status) " +"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Pending')";			
				affectedRows = jdbcTemplate.update(insertQuery, payrollList.getEmpCode(), payrollList.getEmpName(),
						payrollList.getEmpEmail(), payrollList.getPermissionCount(),
						payrollList.getSickLeaveDays(), payrollList.getCasualLeaveDays(),
						payrollList.getTotalCheckinCount(),payrollList.getWorkingHours(), payrollList.getSalary());

				if (affectedRows > 0) {
					System.out.println("Data inserted successfully.");
				} else {
					System.out.println("Data insertion failed.");
				}
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		return affectedRows;
	}

	public List<EmployeePayScale> getAllEmployeePayScales() {
		String getEmpPayScale = "SELECT id, emp_code, username, useremail, payroll_permission,sick_leaveDays, casual_leaveDays, working_days, working_hours,salary,salary_status, gross_pay,Pf, netpay FROM employee_payscale";
		return jdbcTemplate.query(getEmpPayScale, new EmployeePayscaleMapper());
	}

	public List<EmployeePayScale> searchEmployeePayScales(int empCode) {
		String getEmpPayScale = "SELECT id, emp_code, username, useremail, payroll_permission,sick_leaveDays, casual_leaveDays, working_days, working_hours,salary,salary_status, gross_pay,Pf, netpay FROM employee_payscale WHERE emp_code=?";
		return jdbcTemplate.query(getEmpPayScale, new EmployeePayscaleMapper(),empCode);
	}

	public void payrollPays(EmployeePayScale employeePayScale, int empCode) {
		String updateQuery = "UPDATE employee_payscale SET gross_pay=?, Pf=?, netpay=? WHERE emp_code=?";
		int rowCount = jdbcTemplate.update(updateQuery,employeePayScale.getGrossPay(),employeePayScale.getPf(),employeePayScale.getNetPay(),empCode);
	}

	public List<PermissionCount> getPermissionStatus(int empCode){
		String getAllQuery = "SELECT emp_code,name, date, start_time, end_time, status, permission,message_text FROM permission_count WHERE emp_code=?";
		return jdbcTemplate.query(getAllQuery, new PermissionMapper(),empCode);
	}

	public List<LeaveReport> getAllLeaveStatus(int empCode) {
		String getAllQuery = "SELECT id,emp_code, name, from_date, to_date, leave_type, leave_Count,message_text ,status FROM Leave_report WHERE emp_code=?";
		return jdbcTemplate.query(getAllQuery, new LeaveInfoMapper(),empCode);
	}

	public void salaryCredited(int empCode) {
		String updateQuery = "UPDATE employee_payscale SET salary_status='Credited' WHERE emp_code=?";
		int rowCount = jdbcTemplate.update(updateQuery,empCode);
	}


	public List<Employees> getEmployeeDeatils(int empCode) {
		String getAllQuery = "SELECT emp_code,username,designation,useremail,userpassword,usermobile,image,salary FROM Employee_details WHERE emp_code=?";
		return jdbcTemplate.query(getAllQuery, new PayrollMapper(), empCode);	
	}

	public List<AdminReport> getReport(int empCode) {
		String getAllQuery = "SELECT emp_code, name, report_text FROM admin_report WHERE emp_code=?";
		return jdbcTemplate.query(getAllQuery, new AdminReportMapper(),empCode);
	}

	public List<PermissionCount> searchPermission(int empCode){
		String getAllQuery = "SELECT emp_code, name, date, start_time, end_time, status, permission FROM permission_count  WHERE emp_code=?";
		return jdbcTemplate.query(getAllQuery, new PermissionMapper(),empCode);
	}

	public List<LeaveReport> searchLeaveReports(int empCode) {
		String getAllQuery = "SELECT emp_code, name, from_date, to_date, leave_type, leave_Count, status FROM Leave_report WHERE emp_code=? ";
		return jdbcTemplate.query(getAllQuery, new LeaveInfoMapper(),empCode);
	}

	@Override
	public int insertOrUpdateLeavePermission(PayrollList payrollList, int empCode) {
		return 0;
	}

	public void salaryPending(int empCode) {
		String updateQuery = "UPDATE employee_payscale SET salary_status='Pending' WHERE emp_code=?";
		int rowCount = jdbcTemplate.update(updateQuery,empCode);
	}

	public boolean isSalaryCredited(int empCode) {
		String query = "SELECT COUNT(*) FROM employee_payscale WHERE emp_code = ? AND salary_status = 'Credited'";
		int count = jdbcTemplate.queryForObject(query, Integer.class, empCode);
		return count > 0;
	}

	public void permissionCountDeleteByDays(int empCode) {
		String deleteQuery = "DELETE FROM permission_count WHERE emp_code = ?";
		int rowsAffected = jdbcTemplate.update(deleteQuery, empCode);			     
	}

	public void leaveCountDeleteByDays(int empCode) {
		String deleteQuery = "DELETE FROM Leave_report WHERE emp_code = ?";
		int rowsAffected = jdbcTemplate.update(deleteQuery, empCode);			     
	}

	public void checkInsOutsDeleteByDays(int empCode) {
		String deleteQuery = "DELETE FROM checkins_checkouts WHERE emp_code = ?";
		int rowsAffected = jdbcTemplate.update(deleteQuery, empCode);			     
	}

	public void EmpPayscaleDeleteByDays(int empCode) {
		String deleteQuery = "DELETE FROM employee_payscale WHERE emp_code = ?";
		int rowsAffected = jdbcTemplate.update(deleteQuery, empCode);			     
	}
	
	public List<Employees> getExistingEmployeeCodes() {
		String getAllQuery = "SELECT emp_code,username,designation,useremail,userpassword,usermobile,image,salary FROM Employee_details";

	    return jdbcTemplate.query(getAllQuery, new PayrollMapper());
	}
	
    public List<CheckInsAndCheckOuts> getEmployeeCodes(String date) {
        String getEmpCheckIns = "select emp_code,name,checkin_time,checkout_time from checkins_checkouts where DATE(checkin_time) = ?";
        return jdbcTemplate.query(getEmpCheckIns, new CheckInOutHandler() , date);
    }

	public boolean updateTaskList(int empCode, String status) {
		String query = "UPDATE admin_report SET status = ? WHERE emp_code = ?";
		int rowCount = jdbcTemplate.update(query, status, empCode);
		if (rowCount > 0) {
			return true;
		} else {	           
			return false;
		}
	}

	public int getTimeDurations(int empCode) {
	    String getEmpCheckIns = "select time_duration from admin_report where emp_code = ?";
	    return jdbcTemplate.queryForObject(getEmpCheckIns, Integer.class, empCode);
	}

	public void updateTaskDuration(int empCode, int timeDuration) {
	    String updateQuery = "UPDATE admin_report SET time_duration = ? WHERE emp_code = ?";
	    jdbcTemplate.update(updateQuery, timeDuration, empCode);
	}

	public void reasonForRejection(int empCode, String reason) {
	    String updateQuery = "UPDATE admin_report SET reason = ? WHERE emp_code = ?";
	    jdbcTemplate.update(updateQuery, reason, empCode);
	}

	
}








