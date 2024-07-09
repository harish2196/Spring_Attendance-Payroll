package com.chainsys.payrollapplication.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
		Integer empCode = jdbcTemplate.queryForObject(sql,new GetEmpCodeMapper(), username);
		return empCode;	       
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
			String updatePermissionQuery = "UPDATE permission_count SET date = ?, start_time = ?, end_time = ?, status = 'waiting', permission = permission WHERE emp_code = ?";
			jdbcTemplate.update(updatePermissionQuery, permissionCount.getDate(), permissionCount.getStartTime(),
					permissionCount.getEndTime(), empCode);
			System.out.println("Permission count updated successfully.");
		} else {
			String insertPermissionQuery = "INSERT INTO permission_count (emp_code, name, date, start_time, end_time, permission) VALUES (?, ?, ?, ?, ?, 0)";
			Object[] params = { empCode, permissionCount.getName(),
					permissionCount.getDate(), permissionCount.getStartTime(),
					permissionCount.getEndTime()};
			int rowsAffected = jdbcTemplate.update(insertPermissionQuery, params);
			System.out.println("Rows Affected: " + rowsAffected);
		}
	}

	public void insertLeaveReport(LeaveReport leaveReport, int empCode) {
		String sql = "INSERT INTO leave_report (emp_code, name, from_date, to_date, leave_type)  VALUES (?, ?, ?, ?, ?)";
		Object[] params = { empCode, leaveReport.getName(), leaveReport.getFromdate(),
				leaveReport.getTodate(), leaveReport.getLeaveType() };
		int rowsAffected = jdbcTemplate.update(sql, params);      
	}

	public void insertAdminReport(AdminReport adminReport, int empCode) {
		String sql = "INSERT INTO admin_report (emp_code, name, report_text) VALUES (?, ?, ?)";
		Object[] params = { empCode, adminReport.getName(), adminReport.getText() };
		int rowsAffected = jdbcTemplate.update(sql, params);
		System.out.println("Rows Affected: " + rowsAffected);
	}

	public List<Employees> viewEmployeeDetails() {
		String getAllQuery = "SELECT emp_code,username,designation,useremail,userpassword,usermobile,image,salary FROM Employee_details";
		return jdbcTemplate.query(getAllQuery, new PayrollMapper());
	}

	public List<CheckInsAndCheckOuts> viewCheckInsAndOuts() {
		String getAllQuery = "SELECT emp_code,name,checkin_time,checkout_time FROM checkins_checkouts";
		return jdbcTemplate.query(getAllQuery, new CheckInOutHandler());
	}

	public List<AdminReport> getComments() {
		String getAllQuery = "SELECT emp_code, name, report_text FROM admin_report";
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
		String getAllQuery = "SELECT emp_code, name, date, start_time, end_time, status, permission FROM permission_count";
		return jdbcTemplate.query(getAllQuery, new PermissionMapper());
	}

	public List<LeaveReport> getAllLeaveReports() {
		String getAllQuery = "SELECT emp_code, name, from_date, to_date, leave_type, leave_Count, status FROM Leave_report";
		return jdbcTemplate.query(getAllQuery, new LeaveInfoMapper());
	}

	public boolean updatePermissionStatus(int empCode, String status) {
		boolean isSuccess = false;

		String updateQuery = "UPDATE permission_count SET status = ? WHERE emp_code = ?";
		int rowCount = jdbcTemplate.update(updateQuery, status, empCode);

		if (rowCount > 0) {
			System.out.println("Data updated successfully.");
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

	public int remainRejectLeaveDays(int empCode) {
		String query = "SELECT SUM(DATEDIFF(to_date, from_date)) AS total_days FROM Leave_report WHERE emp_code = ?";
		Integer totalLeaveDays = jdbcTemplate.queryForObject(query, Integer.class,empCode);
		if (totalLeaveDays != null) {
			return totalLeaveDays - 1;
		} else {
			return 0; 
		}
	}

	public int getTotalLeaveDays(int empCode) {
		String query = "SELECT SUM(DATEDIFF(to_date, from_date)) AS total_days FROM Leave_report WHERE emp_code = ?";
		Integer totalLeaveDays = jdbcTemplate.queryForObject(query, Integer.class,empCode);
		if (totalLeaveDays != null) {
			return totalLeaveDays;
		} else {
			return 0; 
		}
	}

	public boolean insertTotalLeaveDays(int empCode, int totalLeaveDays) {
		String updateQuery = "UPDATE Leave_report SET leave_Count = ? WHERE emp_code = ?";
		int rowCount = jdbcTemplate.update(updateQuery, totalLeaveDays, empCode);

		if (rowCount > 0) {
			return true;
		} else {	       
			return false; 
		}
	}

	public boolean updateLeaveStatus(int empCode, String status) {
		String query = "UPDATE Leave_report SET status = ? WHERE emp_code = ?";
		int rowCount = jdbcTemplate.update(query, status, empCode);
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
		String sumQuery ="SELECT SUM(TIMESTAMPDIFF(MINUTE, checkin_time, checkout_time)) / 60 AS total_minutes FROM checkins_checkouts WHERE emp_code = ?";
		int count = jdbcTemplate.queryForObject(sumQuery, Integer.class, empCode);
		return count;			
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
		String getPermission = "SELECT permission  FROM permission_count WHERE emp_code = ?";
		try {
			return jdbcTemplate.queryForObject(getPermission, Integer.class, empCode);		  
		}catch(Exception e) {
			return 0;
		}
	}

	public int countSickLeavePayroll(int empCode) {
		String sickLeave = "SELECT SUM(DATEDIFF(to_date, from_date)) AS sick_leave_days FROM Leave_report WHERE emp_code = ? AND leave_type = 'sick'";
		try {
			return jdbcTemplate.queryForObject(sickLeave, Integer.class, empCode);	
		}catch(Exception e) {
			return 0;
		}
	}

	public int countCasualLeavePayroll(int empCode) {
		String casualLeave = "SELECT SUM(DATEDIFF(to_date, from_date)) AS casual_leave_days FROM Leave_report WHERE emp_code = ? AND leave_type = 'casual'";
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
		String getAllQuery = "SELECT emp_code,name, date, start_time, end_time, status, permission FROM permission_count WHERE emp_code=?";
		return jdbcTemplate.query(getAllQuery, new PermissionMapper(),empCode);
	}

	public List<LeaveReport> getAllLeaveStatus(int empCode) {
		String getAllQuery = "SELECT emp_code, name, from_date, to_date, leave_type, leave_Count, status FROM Leave_report WHERE emp_code=?";
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
		// TODO Auto-generated method stub
		return 0;
	}
}








