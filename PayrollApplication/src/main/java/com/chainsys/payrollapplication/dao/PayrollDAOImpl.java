package com.chainsys.payrollapplication.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.payrollapplication.mapper.GetEmpCodeMapper;
import com.chainsys.payrollapplication.mapper.LoginMapper;
import com.chainsys.payrollapplication.mapper.PayrollMapper;
import com.chainsys.payrollapplication.model.AdminReport;
import com.chainsys.payrollapplication.model.EmployeeDetails;
import com.chainsys.payrollapplication.model.LeaveReport;
import com.chainsys.payrollapplication.model.PermissionCount;

@Repository
public class PayrollDAOImpl implements PayrollDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	PayrollMapper payrollMapper;
	LoginMapper loginMapper;
	GetEmpCodeMapper getEmpCodeMapper;

	public void insertEmployee(EmployeeDetails employeeDetails) {
		String insertQuery = "INSERT INTO Employee_details (emp_code, username, designation, useremail, userpassword, usermobile, image, salary) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = { employeeDetails.getEmp_code(), employeeDetails.getUsername(), 
				employeeDetails.getDesignation(), employeeDetails.getUseremail(), 
				employeeDetails.getUserpassword(), employeeDetails.getUsermobile(), 
				employeeDetails.getImageData(), employeeDetails.getSalary() };

		int rowsAffected = jdbcTemplate.update(insertQuery, params);
		System.out.println("Rows Affected: " + rowsAffected);
	}

	public  boolean login(String username, String password) {
		String sql = "SELECT username,userpassword FROM Employee_details WHERE username = ? AND userpassword = ?";
		EmployeeDetails count = jdbcTemplate.queryForObject(sql, new LoginMapper(), username, password);
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
			jdbcTemplate.update(updatePermissionQuery, permissionCount.getDate(), permissionCount.getStart_time(),
					permissionCount.getEnd_time(), empCode);
			System.out.println("Permission count updated successfully.");
		} else {
			String insertPermissionQuery = "INSERT INTO permission_count (emp_code, name, date, start_time, end_time, permission) VALUES (?, ?, ?, ?, ?, 0)";
			Object[] params = { empCode, permissionCount.getName(),
					permissionCount.getDate(), permissionCount.getStart_time(),
					permissionCount.getEnd_time()};
			int rowsAffected = jdbcTemplate.update(insertPermissionQuery, params);
			System.out.println("Rows Affected: " + rowsAffected);
		}
	}

	  public void insertLeaveReport(LeaveReport leaveReport, int empCode) {
	        String sql = "INSERT INTO leave_report (emp_code, name, from_date, to_date, leave_type)  VALUES (?, ?, ?, ?, ?)";
	        Object[] params = { empCode, leaveReport.getName(), leaveReport.getFromdate(),
	                            leaveReport.getToDate(), leaveReport.getLeaveType() };
	        int rowsAffected = jdbcTemplate.update(sql, params);      
	}

	    public void insertAdminReport(AdminReport adminReport, int empCode) {
	        String sql = "INSERT INTO admin_report (emp_code, name, report_text) VALUES (?, ?, ?)";
	        Object[] params = { empCode, adminReport.getName(), adminReport.getText() };
	        int rowsAffected = jdbcTemplate.update(sql, params);
	        System.out.println("Rows Affected: " + rowsAffected);
	    }



}



