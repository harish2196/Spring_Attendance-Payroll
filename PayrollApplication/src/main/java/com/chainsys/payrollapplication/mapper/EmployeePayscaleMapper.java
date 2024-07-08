package com.chainsys.payrollapplication.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.payrollapplication.model.EmployeePayScale;

public class EmployeePayscaleMapper implements RowMapper<EmployeePayScale> {

    @Override
    public EmployeePayScale mapRow(ResultSet rs, int rowNum) throws SQLException {
    	EmployeePayScale employeePayscale = new EmployeePayScale();
        employeePayscale.setId(rs.getInt("id"));
        employeePayscale.setEmpCode(rs.getInt("emp_code"));
        employeePayscale.setUsername(rs.getString("username"));
        employeePayscale.setUserEmail(rs.getString("useremail"));
        employeePayscale.setPayrollPermission(rs.getString("payroll_permission"));
        employeePayscale.setSickLeaveDays(rs.getInt("sick_leaveDays"));
        employeePayscale.setCasualLeaveDays(rs.getInt("casual_leaveDays"));
        employeePayscale.setWorkingDays(rs.getInt("working_days"));
        employeePayscale.setWorkingHours(rs.getInt("working_hours"));
        employeePayscale.setSalary(rs.getInt("salary"));
        employeePayscale.setSalaryStatus(rs.getString("salary_status"));
        employeePayscale.setGrossPay(rs.getInt("gross_pay"));
        employeePayscale.setPf(rs.getInt("Pf"));
        employeePayscale.setNetPay(rs.getInt("netpay"));

        return employeePayscale;
    }

}

