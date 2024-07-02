package com.chainsys.payrollapplication.mapper;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.payrollapplication.model.Employees;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PayrollMapper implements RowMapper<Employees> {

    @Override
    public Employees mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Employees employeeDetails = new Employees();
        employeeDetails.setEmpCode(rs.getInt("emp_code"));
        employeeDetails.setUserName(rs.getString("username"));
        employeeDetails.setDesignation(rs.getString("designation"));
        employeeDetails.setUserEmail(rs.getString("useremail"));
        employeeDetails.setUserPassword(rs.getString("userpassword"));
        employeeDetails.setUserMobile(rs.getString("usermobile"));
       employeeDetails.setImageData(rs.getBytes("image"));
        employeeDetails.setSalary(rs.getInt("salary"));
        return employeeDetails;
    }
   
}
